package wyc.block.constant;

public class BlockConstant {

    public static final int BLOCK_INDEX=0;//redis 0号库，用于存放区块信息
    public static final int LAST_HASH_INDEX=1;//redis 1号库，用于存放最新区块的hash
    public static final int UTXO_INDEX=2;//redis 2号库，用于存放utxo集合
    public static final String LAST_HASH_KEY="L";//最新区块的hash 的key

    //在比特币中，第一笔 coinbase 交易信息
    public static final String GENESIS_COINBASE_DATA="The Times 03/Jan/2009 Chancellor on brink of second bailout for banks";
}
