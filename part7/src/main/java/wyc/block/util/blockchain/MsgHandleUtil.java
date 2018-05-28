package wyc.block.util.blockchain;

import org.apache.log4j.Logger;
import wyc.block.constant.BlockConstant;
import wyc.block.entity.Block;
import wyc.block.entity.Transaction;
import wyc.block.entity.net.*;
import wyc.block.util.DataUtil;
import wyc.block.util.ServerUtil;
import wyc.block.util.encrypt.SerializeUtil;
import wyc.block.util.transacation.TransactionUtil;
import wyc.block.util.transacation.UTXOUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MsgHandleUtil {
    private static List<byte[]> blocksInTransit;
    private static Logger logger = Logger.getLogger(MsgHandleUtil.class);
    public static  void msgHandle(byte[] msg){

        String command = DataUtil.bytes2String(DataUtil.subBytes(msg,2,2));

        byte[] data =DataUtil.subBytes(msg,4,msg.length-4);
        if(command.equals(DataUtil.bytes2String(BlockConstant.VERSION))){
            versionHandle(data);
        }else if(command.equals(DataUtil.bytes2String(BlockConstant.GET_BLOCK))){
            getBlocksHandle(data);
        }else if(command.equals(DataUtil.bytes2String(BlockConstant.INV))){
            invHandle(data);
        }else if(command.equals(DataUtil.bytes2String(BlockConstant.GET_DATA))){
            getDataHandle(data);
        }else if(command.equals(DataUtil.bytes2String(BlockConstant.BLOCK))){
            blockHandle(data);
        }else if(command.equals(DataUtil.bytes2String(BlockConstant.ADDRESS))){
            addressHandle(data);
        }else if(command.equals(DataUtil.bytes2String(BlockConstant.TX))){
            txHandle(data);
        }else{
            logger.info("handle type error!");
        }
    }
    private static void versionHandle(byte[] data){
        Version version = (Version)SerializeUtil.deserialize(data);
        logger.info("Recive VersionHandle From "+version.getIpFrom());
        int myBestHeight = BlockChainUtil.getBlockHeight();
        int foreignerBestHeight = version.getBestHeight();
        if(myBestHeight < foreignerBestHeight){
            ServerUtil.sendGetBlocks(version.getIpFrom());
        }else if(myBestHeight > foreignerBestHeight){
            ServerUtil.sendVersion(version.getIpFrom());
        }
        ServerUtil.sendAddress(version.getIpFrom());
        KnownNodes.addKnownNodes(version.getIpFrom());
    }
    public static void addressHandle(byte[] data){
        Address address = (Address)SerializeUtil.deserialize(data);
        logger.info("Recive AddressHandle From " +address.getAddress());
        List<String> addressList = address.getAddress();
        for(int i=0;i<addressList.size();i++){
            KnownNodes.addKnownNodes(addressList.get(i));
        }
        logger.info("There are "+KnownNodes.getKnownNodes().size()+" known nodes now!");
        logger.info(KnownNodes.getKnownNodes());
    }
    public static void getBlocksHandle(byte[] data){
        String addrFrom = DataUtil.bytes2String(data);
        logger.info("Recieve GetBlocksHandle From "+addrFrom);
        List<byte[]> blocksHashes = BlockChainUtil.getBlocksHashes();
        ServerUtil.sendInv(addrFrom,"block",blocksHashes);
    }
    public static void invHandle(byte[] data){
        Inventory inventory = (Inventory)SerializeUtil.deserialize(data);
        String type =inventory.getType();
        logger.info("Recieve InvHandle From "+inventory.getAddrFrom());
        logger.info("Recevied inventory with "+inventory.getItems().size()+" "+type);
        if("block".equals(type)){
            blocksInTransit=inventory.getItems();
            blocksInTransit = inventory.getItems();
            byte[] blockHash = inventory.getItems().get(0);
            ServerUtil.sendGetData(inventory.getAddrFrom(),type,blockHash);
            List<byte[]> newInTransit = new ArrayList<byte[]>();
            for(byte[] b : blocksInTransit){
                if(!Arrays.equals(b,blockHash)){
                    newInTransit.add(b);
                }
            }
            blocksInTransit = newInTransit;
        }
    }
    public static void getDataHandle(byte[] data){
        GetData getData =  (GetData)SerializeUtil.deserialize(data);
        logger.info("Recive GetDataHandle From "+ getData.getAddrFrom());
        String type =getData.getType();
        if("block".equals(type)){
            Block block = BlockUtil.getBlockByHash(getData.getId());
            ServerUtil.sendBlock(getData.getAddrFrom(),block);
        }
        if("tx".equals(type)){
            String txID = DataUtil.byte2Hex(getData.getId());
            Transaction tx =  ServerUtil.getMempool().get(txID);
            ServerUtil.sendTx(getData.getAddrFrom(),tx);
        }
    }

    public static void blockHandle(byte[] data){
        NetBlock netBlock =(NetBlock)SerializeUtil.deserialize(data);
        logger.info("Recive BlockHandle From "+ netBlock.getAddrFrom());
        Block block = netBlock.getBlock();
        logger.info("Recevied a new block!");
        BlockChainUtil.addBlock(block);
        logger.info("Added block "+DataUtil.byte2Hex(block.getHash()));
        if(blocksInTransit.size()>0){
            byte[] blockHash = blocksInTransit.get(0);
            ServerUtil.sendGetData(netBlock.getAddrFrom(),"block",blockHash);
            blocksInTransit.remove(blocksInTransit.get(0));
        }else{
            UTXOUtil.reIndex();
        }
    }


    public static void txHandle(byte[] data){
        Tx tx = (Tx)SerializeUtil.deserialize(data);
        logger.info("Recive TxHandle From "+tx.getAddrFrom());
        Transaction transaction = tx.getTransaction();
        ServerUtil.getMempool().put(DataUtil.byte2Hex(transaction.getId()),transaction);
        if(KnownNodes.getKnownNodes().get(0).equals(ServerUtil.getNodeAddress())){
            for(String node : KnownNodes.getKnownNodes()){
                if(!node.equals(ServerUtil.getNodeAddress()) && !node.equals(tx.getAddrFrom())){
                    List<byte[]> hash = new ArrayList<byte[]>();
                    hash.add(transaction.getId());
                    ServerUtil.sendInv(node,"tx",hash);
                }
            }
        }else{
            if(ServerUtil.getMempool().size()>=2 && ServerUtil.getMiningAddress().length()>0){
                try{

                    while(ServerUtil.getMempool().size()>0){
                        List<Transaction> txs = new ArrayList<Transaction>();

                        for(String id : ServerUtil.getMempool().keySet()){
                            if(TransactionUtil.verifyTransaction(ServerUtil.getMempool().get(id))){
                                txs.add(ServerUtil.getMempool().get(id));
                            }
                        }
                        if(txs.size()==0){
                            logger.info("All transactions are invalid! Waiting for new ones...");
                            return;
                        }

                        Transaction cbTx = TransactionUtil.getNewCoinbaseTx(ServerUtil.getMiningAddress(),"");
                        txs.add(cbTx);

                        Block newBlock = BlockChainUtil.mineBlock(txs);
                        UTXOUtil.reIndex();
                        logger.info("New block is mined!");

                        for(Transaction transaction_: txs){
                            ServerUtil.getMempool().remove(DataUtil.byte2Hex(transaction_.getId()));
                        }
                        for(String node : KnownNodes.getKnownNodes()){
                            if(!node.equals(ServerUtil.getNodeAddress())){
                                List<byte[]> hash = new ArrayList<byte[]>();
                                hash.add(newBlock.getHash());
                                ServerUtil.sendInv(node,"block",hash);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }



    public static void main(String[] args){
    }
}
