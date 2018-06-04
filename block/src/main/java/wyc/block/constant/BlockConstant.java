package wyc.block.constant;

public class BlockConstant {

    public static final int BLOCK_INDEX=0;//redis 0号库，用于存放区块信息
    public static final int LAST_HASH_INDEX=1;//redis 1号库，用于存放最新区块的hash
    public static final int UTXO_INDEX=2;//redis 2号库，用于存放utxo集合
    public static final String LAST_HASH_KEY="L";//最新区块的hash 的key

    public static final int nodeVersion =1;

    //在比特币中，第一笔 coinbase 交易信息
    public static final String GENESIS_COINBASE_DATA="The Times 03/Jan/2009 Chancellor on brink of second bailout for banks";

    public static byte[] VERSION = "00".getBytes();
    public static byte[] GET_BLOCK = "01".getBytes();
    public static byte[] INV = "02".getBytes();
    public static byte[] GET_DATA = "03".getBytes();
    public static byte[] BLOCK = "04".getBytes();
    public static byte[] ADDRESS = "05".getBytes();
    public static byte[] TX = "06".getBytes();


}
