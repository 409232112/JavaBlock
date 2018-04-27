package wyc.block.util.blockchain;

import org.apache.log4j.Logger;
import wyc.block.constant.WalletConstant;
import wyc.block.entity.Transaction;
import wyc.block.entity.Wallet;
import wyc.block.util.DataUtil;
import wyc.block.util.FileUtil;
import wyc.block.util.encrypt.Base58Util;
import wyc.block.util.transacation.TransactionUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WalletUtil {

    private static Map<String,Wallet> wallets;
    private static final  String walletFilePath = WalletConstant.WALLET_FILE_DIR+WalletConstant.WALLET_FILE;
    private static Logger logger = Logger.getLogger(WalletUtil.class);

    static{
        init();
    }

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
    public static byte[] hashPubKey(byte[] pubKeyHash) throws Exception{
        byte[] publicSHA256 = DataUtil.getSHA256Bytes(pubKeyHash);
        return DataUtil.encodeRipeMD160(publicSHA256);
    }
    private static byte[] checksum(byte[] payload){
        byte[] firstSHA = DataUtil.getSHA256Bytes(payload);
        byte[] secondSHA = DataUtil.getSHA256Bytes(firstSHA);
        byte[] retBytes = DataUtil.subBytes(secondSHA,0,4);
        return retBytes;
    }

    public static boolean validateAddress(String address){
        byte[] pubKeyHash = Base58Util.decode(address);
        byte[] actualChecksum =DataUtil.subBytes(pubKeyHash,pubKeyHash.length-WalletConstant.ADDRESS_CHECKSUM_LEN,WalletConstant.ADDRESS_CHECKSUM_LEN);
        byte version = pubKeyHash[0];
        byte[] versionBytes = {version};
        pubKeyHash = DataUtil.subBytes(pubKeyHash,1,pubKeyHash.length-WalletConstant.ADDRESS_CHECKSUM_LEN-1);
        byte[] targetChecksum=checksum(DataUtil.combineBytes(versionBytes,pubKeyHash));
        return Arrays.equals(actualChecksum,targetChecksum);
    }

    public static Wallet getWallet(String from){
        return wallets.get(from);
    }
    public static String createWallet() throws Exception{
        Wallet wallet = new Wallet();
        String address =DataUtil.bytes2String(getAddress(wallet));
        addToWallets(wallet,address);
        return address;
    }


    private static void addToWallets(Wallet wallet,String address){
        wallets.put(address,wallet);
        saveWalletsToFile();
    }

    private static void saveWalletsToFile(){
        FileUtil.writeObject(wallets,walletFilePath);
    }

    private static Map<String, Wallet> readWalletsFromFile(){
        return (Map<String, Wallet>)FileUtil.readObject(walletFilePath);
    }

    private static void init(){
        FileUtil.createDir(WalletConstant.WALLET_FILE_DIR);
        FileUtil.createFile(walletFilePath);
        Object o = readWalletsFromFile();
        wallets = o==null?new ConcurrentHashMap<String, Wallet>():(ConcurrentHashMap<String, Wallet>)o;
    }


    public static void  printWallet() throws Exception{
        logger.info("=====================遍历钱包===============================");
        for (Map.Entry<String,Wallet> entry : wallets.entrySet()) {
            logger.info("address:"+entry.getKey());
            TransactionUtil.getBalance(entry.getKey());
        }
        logger.info("===========================================================");
    }
}
