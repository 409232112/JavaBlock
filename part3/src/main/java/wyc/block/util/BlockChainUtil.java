package wyc.block.util;

import org.apache.log4j.Logger;
import wyc.block.constant.BlockConstant;
import wyc.block.entity.Block;
import wyc.block.entity.ProofOfWork;

public class BlockChainUtil {

    private static Logger logger = Logger.getLogger(BlockChainUtil.class);

    /**
     * 初始化区块链，加入创世区块
     */
    static{
        Object lastHashO = RedisUtil.get(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY);
        if(lastHashO==null){
            saveBlock(BlockUtil.getNewGenesisBlock());//加入存储创世区块
        }
    }

    /**
     * 向链中加入一个新块
     * @param data 在实际中就是交易
     */
    public static void addBlock(String data){
        Block prevBlock =getLastBlock();
        Block newBlock =BlockUtil.getNewBlock(DataUtil.string2Bytes(data), prevBlock.getHash());
        saveBlock(newBlock);
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
     * 将区块信息持久化至redis
     */
    public static void saveBlock(Block block){
        byte[] key = block.getHash();
        RedisUtil.set(BlockConstant.BLOCK_INDEX,key,BlockUtil.serialize(block));
        RedisUtil.set(BlockConstant.LAST_HASH_INDEX,BlockConstant.LAST_HASH_KEY,key);
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
            logger.info("No Block Found!!");
        }
    }
    private static void printBlock(byte[] hash){

        Block block = BlockUtil.deserialize(RedisUtil.get(BlockConstant.BLOCK_INDEX,hash));
        System.out.print(block.toString());
        ProofOfWork proofOfWork = ProofOfWorkUtil.getNewProofOfWork(block);
        System.out.println("PoW "+ ProofOfWorkUtil.validate(proofOfWork));
        if(block.getPrevBlockHash().length!=0){
            System.out.println("--------------------------------------------------");
            printBlock(block.getPrevBlockHash());
        }
    }
}
