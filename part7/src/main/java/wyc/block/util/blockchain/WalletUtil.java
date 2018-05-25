package wyc.block.util.blockchain;

import org.apache.log4j.Logger;
import wyc.block.constant.WalletConstant;
import wyc.block.entity.Wallet;
import wyc.block.util.DataUtil;
import wyc.block.util.FileUtil;
import wyc.block.util.encrypt.Base58Util;
import wyc.block.util.transacation.TransactionUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WalletUtil {

    private static Map<String,Wallet> wallets;   //钱包池
    private static final  String walletFilePath = WalletConstant.WALLET_FILE_DIR+WalletConstant.WALLET_FILE; //钱包文件完整路径
    private static Logger logger = Logger.getLogger(WalletUtil.class);

    static{
        init();
    }

    /**
     * 初始化
     */
    private static void init(){
        FileUtil.createDir(WalletConstant.WALLET_FILE_DIR);
        FileUtil.createFile(walletFilePath);
        Object o = readWalletsFromFile();
        wallets = o==null?new ConcurrentHashMap<String, Wallet>():(ConcurrentHashMap<String, Wallet>)o;
    }

    /**
     * 获取钱包地址
     * @param wallet
     * @return
     * @throws Exception
     */
    public static byte[] getAddress(Wallet wallet) throws Exception{
        //1、使用 RIPEMD160(SHA256(PubKey)) 哈希算法，取公钥并对其哈希两次
        byte[] pubKeyHash = hashPubKey(wallet.getPublicKey().getEncoded());
        //2、给哈希加上地址生成算法版本的前缀
        byte[] version ={WalletConstant.VERSION};
        byte[] versionedPayload = DataUtil.combineBytes(version,pubKeyHash);
        //3、对于第二步生成的结果，使用 SHA256(SHA256(payload)) 再哈希，计算校验和。校验和是结果哈希的前四个字节。
        byte[] checksum = checksum(versionedPayload);
        //4、将校验和附加到 version+PubKeyHash 的组合中。
        byte[] fullPayload = DataUtil.combineBytes(versionedPayload,checksum);
        //5、使用 Base58 对 version+PubKeyHash+checksum 组合进行编码
        byte[] address = Base58Util.encode2Bytes(fullPayload);
        return address;
    }
    public static String getAddressString(Wallet wallet) throws Exception{
        return DataUtil.bytes2String(getAddress(wallet));
    }

    /**
     * 对公钥进行哈希、摘要算法
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static byte[] hashPubKey(byte[] pubKey) throws Exception{
        byte[] publicSHA256 = DataUtil.getSHA256Bytes(pubKey);
        return DataUtil.encodeRipeMD160(publicSHA256);
    }
    private static byte[] checksum(byte[] payload){
        byte[] firstSHA = DataUtil.getSHA256Bytes(payload);
        byte[] secondSHA = DataUtil.getSHA256Bytes(firstSHA);
        byte[] retBytes = DataUtil.subBytes(secondSHA,0,4);
        return retBytes;
    }

    /**
     * 校验钱包地址是不是满足规则
     * @param address
     * @return
     */
    public static boolean validateAddress(String address){
        byte[] pubKeyHash = Base58Util.decode(address);
        byte[] actualChecksum =DataUtil.subBytes(pubKeyHash,pubKeyHash.length-WalletConstant.ADDRESS_CHECKSUM_LEN,WalletConstant.ADDRESS_CHECKSUM_LEN);
        byte version = pubKeyHash[0];
        byte[] versionBytes = {version};
        pubKeyHash = DataUtil.subBytes(pubKeyHash,1,pubKeyHash.length-WalletConstant.ADDRESS_CHECKSUM_LEN-1);
        byte[] targetChecksum=checksum(DataUtil.combineBytes(versionBytes,pubKeyHash));
        return Arrays.equals(actualChecksum,targetChecksum);
    }

    /**
     * 根据钱包地址获取钱包
     * @param from
     * @return
     */
    public static Wallet getWallet(String from){
        return wallets.get(from);
    }

    /**
     * 创建钱包
     * @return 钱包地址
     * @throws Exception
     */
    public static String createWallet() throws Exception{
        Wallet wallet = new Wallet();
        String address =DataUtil.bytes2String(getAddress(wallet));
        addToWallets(wallet);
        return address;
    }

    /**
     * 将钱包存入钱包池中，并持久化到硬盘文件中
     * @param wallet
     * @throws Exception
     */
    private static void addToWallets(Wallet wallet)  throws Exception{
        String address =DataUtil.bytes2String(getAddress(wallet));
        wallets.put(address,wallet);
        saveWalletsToFile();
    }

    /**
     * 将所有钱包存入文件
     */
    private static void saveWalletsToFile(){
        FileUtil.writeObject(wallets,walletFilePath);
    }

    /**
     * 从硬盘读取钱包池
     * @return
     */
    private static Map<String, Wallet> readWalletsFromFile(){
        return (Map<String, Wallet>)FileUtil.readObject(walletFilePath);
    }

    /**
     * 打印钱包
     * @throws Exception
     */
    public static void  printWallet() throws Exception{
        logger.info("=====================遍历钱包===============================");
        for (Map.Entry<String,Wallet> entry : wallets.entrySet()) {
            TransactionUtil.getBalance(entry.getKey());
        }
        logger.info("===========================================================");
    }
}
