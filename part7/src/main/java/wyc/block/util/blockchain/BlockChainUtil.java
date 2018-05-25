package wyc.block.util.blockchain;

import org.apache.log4j.Logger;
import wyc.block.constant.BlockConstant;
import wyc.block.entity.*;
import wyc.block.util.DataUtil;
import wyc.block.util.RedisUtil;
import wyc.block.util.transacation.TransactionUtil;
import wyc.block.util.transacation.UTXOUtil;

import java.util.*;

public class BlockChainUtil {

    private static Logger logger = Logger.getLogger(BlockChainUtil.class);

    /**
     * 创建区块链
     */
    public static void createBlockChain(String address){
        Object lastHashO = RedisUtil.get(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY);
        if(lastHashO==null){
            saveBlock(BlockUtil.getNewGenesisBlock(TransactionUtil.getNewCoinbaseTx(BlockConstant.GENESIS_COINBASE_DATA,address)));//加入存储创世区块
            UTXOUtil.reIndex();
        }else{
            logger.info("Blockchain already exists.");
        }
    }



    /**
     * 获取最新的区块
     * @return
     */
    public static Block getLastBlock(){
        Object lastHashO = RedisUtil.get(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY);
        if(lastHashO!=null){
            return BlockUtil.deserialize(RedisUtil.get(BlockConstant.BLOCK_INDEX, (byte[])lastHashO));
        }else{
            logger.info("Can Not Get Last Block!!");
            return null;
        }
    }

    /**
     * 获取最新区块hash
     * @return
     */
    public static byte[] getLastHash(){
        Object lastHashO = RedisUtil.get(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY);
        return lastHashO!=null?(byte[])lastHashO:null;
    }

    /**
     * 将区块信息持久化至redis
     */
    public static void saveBlock(Block block){
        byte[] key = block.getHash();
        RedisUtil.set(BlockConstant.BLOCK_INDEX,key,BlockUtil.serialize(block));
        RedisUtil.set(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY,key);
    }

    /**
     * 交易挖矿生成新区块
     * @param transactions
     */
    public static Block mineBlock(List<Transaction> transactions){
        Block block = null;

        try{
          for(Transaction transaction:transactions){
                if(!TransactionUtil.verifyTransaction(transaction)){
                    logger.error("ERROR: Invalid transaction");
                    System.exit(1);
                }
            }

            byte[] lastHash =getLastHash();
            int blockHeight = getBlockHeight();
            block = BlockUtil.getNewBlock(transactions,lastHash,blockHeight+1);
            saveBlock(block);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Mine Block Error!!");
        }
        return block;
    }

    /**
     * 打印区块链中数据
     */
    public static void printChain() {
        Object lastHashO = RedisUtil.get(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY);
        if(lastHashO!=null){
            System.out.println("###########################打印区块链########################");
            printBlock((byte[])lastHashO);
            System.out.println("############################打印完毕#########################");
        }else{
            logger.info("No existing blockchain found. Create one first!");
            System.exit(1);
        }
    }
    private static void printBlock(byte[] hash){
        Block block = BlockUtil.deserialize(RedisUtil.get(BlockConstant.BLOCK_INDEX,hash));
        System.out.print(block.toString());
        ProofOfWork proofOfWork = ProofOfWorkUtil.getNewProofOfWork(block);
        System.out.println("PoW "+ ProofOfWorkUtil.validate(proofOfWork));
        if(block.getPrevBlockHash().length!=0){
            printBlock(block.getPrevBlockHash());
        }
    }


    /**
     * 找到链中所有未话费的输出
     * @return
     */
    public static Map<String,List<TxOutput>> findUTXO(){
        Map<String,List<TxOutput>> UTXO = new HashMap<String,List<TxOutput>>();
        Map<String ,List<Integer>> spentTXOs = new HashMap<String ,List<Integer>>();
        Object lastHashO = RedisUtil.get(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY);

        if(lastHashO!=null){
            byte[] hash = (byte[])lastHashO;
            while(hash.length>0){
                Block block = BlockUtil.getBlockByHash(hash);
                List<Transaction> txs = block.getTransactions();
                for(int i=0;i<txs.size();i++){
                    Transaction tx = txs.get(i);
                    String txId = DataUtil.byte2Hex(tx.getId());
                    Outputs:
                    for(int outIdx=0;outIdx<tx.getvOuts().size();outIdx++){
                        if(spentTXOs.get(txId)!=null){
                            for(int spentOutIdx:spentTXOs.get(txId)){
                                if(spentOutIdx == outIdx){
                                    continue Outputs;
                                }
                            }
                        }
                        List<TxOutput> txOutputs =new ArrayList<TxOutput>();
                        if( UTXO.get(txId)!=null){
                            txOutputs = UTXO.get(txId);
                        }
                        txOutputs.add(tx.getvOuts().get(outIdx));
                        UTXO.put(txId,txOutputs);
                    }
                    if(!TransactionUtil.isCoinbase(tx)){
                        for(TxInput txInput : tx.getvIns()){
                            String inTxID  = DataUtil.byte2Hex(txInput.getTxId());
                            List<Integer> vouts = new ArrayList<Integer>();
                            if(spentTXOs.get(inTxID)!=null){
                                vouts = spentTXOs.get(inTxID);
                            }
                            vouts.add(txInput.getVout());
                            spentTXOs.put(inTxID,vouts);
                        }
                    }
                }
                hash=block.getPrevBlockHash();
            }
        }else{
            logger.info("No existing blockchain found. Create one first!");
        }
        return UTXO;
    }

    public static int getBlockHeight(){
        return getLastBlock()==null?0:getLastBlock().getHeight();
    }

    public static List<byte[]> getBlocksHashes(){
        List<byte[]> blocksHashes = new ArrayList<byte[]>();
        Object lastHashO = RedisUtil.get(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY);
        if(lastHashO!=null){
            byte[] hash = (byte[])lastHashO;
            while(hash.length>0){
                Block block = BlockUtil.getBlockByHash(hash);
                blocksHashes.add(block.getHash());
                hash=block.getPrevBlockHash();
            }
        }else{
            logger.info("No existing blockchain found. Create one first!");
            System.exit(1);
        }
        return blocksHashes;
    }

    public static void addBlock(Block block){
        int myBestHeight = getBlockHeight();
        if(block.getHeight()>myBestHeight){
            saveBlock(block);
        }else{
            byte[] key = block.getHash();
            RedisUtil.set(BlockConstant.BLOCK_INDEX,key,BlockUtil.serialize(block));
        }
    }
}
