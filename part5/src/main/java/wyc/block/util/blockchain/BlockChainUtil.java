package wyc.block.util.blockchain;

import org.apache.log4j.Logger;
import wyc.block.constant.BlockConstant;
import wyc.block.entity.Block;
import wyc.block.entity.ProofOfWork;
import wyc.block.entity.Transaction;
import wyc.block.util.RedisUtil;
import wyc.block.util.transacation.TransactionUtil;

import java.util.List;

public class BlockChainUtil {

    private static Logger logger = Logger.getLogger(BlockChainUtil.class);

    /**
     * 创建区块链
     */
    public static void createBlockChain(String address){
        Object lastHashO = RedisUtil.get(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY);
        if(lastHashO==null){
            saveBlock(BlockUtil.getNewGenesisBlock(TransactionUtil.getNewCoinbaseTx(BlockConstant.GENESIS_COINBASE_DATA,address)));//加入存储创世区块
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
    private static byte[] getLastHash(){
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
    public static void mineBlock(List<Transaction> transactions){
        try{
            for(Transaction transaction:transactions){
                if(!TransactionUtil.verifyTransaction(transaction)){
                    logger.error("ERROR: Invalid transaction");
                    System.exit(1);
                }
            }

            byte[] lastHash =getLastHash();
            Block block = BlockUtil.getNewBlock(transactions,lastHash);
            saveBlock(block);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Mine Block Error!!");
        }
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
            System.out.println("-------------------------------------------------- \n");
            printBlock(block.getPrevBlockHash());
        }
    }
}
