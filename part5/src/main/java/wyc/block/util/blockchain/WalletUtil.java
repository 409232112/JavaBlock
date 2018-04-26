package wyc.block.util.blockchain;

import wyc.block.constant.WalletConstant;
import wyc.block.entity.Wallet;
import wyc.block.entity.Wallets;
import wyc.block.util.DataUtil;
import wyc.block.util.encrypt.Base58Util;

import java.security.interfaces.ECPublicKey;
import java.util.Arrays;

public class WalletUtil {
    public static byte[] getAddress(Wallet wallet) throws Exception{
        //1、使用 RIPEMD160(SHA256(PubKey)) 哈希算法，取公钥并对其哈希两次
        byte[] pubKeyHash = hashPubKey(wallet.getPublicKey());
        //2、给哈希加上地址生成算法版本的前缀
        byte[] versionedPayload = new byte[pubKeyHash.length+1];
        byte[] version ={WalletConstant.VERSION};
        System.arraycopy(version, 0, versionedPayload, 0, version.length);
        System.arraycopy(pubKeyHash, 0, versionedPayload, 1, pubKeyHash.length);
        //3、对于第二步生成的结果，使用 SHA256(SHA256(payload)) 再哈希，计算校验和。校验和是结果哈希的前四个字节。
        byte[] checksum = checksum(versionedPayload);
        //4、将校验和附加到 version+PubKeyHash 的组合中。
        byte[] fullPayload = new byte[versionedPayload.length+checksum.length];
        System.arraycopy(versionedPayload, 0, fullPayload, 0, versionedPayload.length);
        System.arraycopy(checksum, 0, fullPayload, versionedPayload.length, checksum.length);
        //5、使用 Base58 对 version+PubKeyHash+checksum 组合进行编码
        byte[] address = Base58Util.encode2Bytes(fullPayload);
        return address;
    }
    public static byte[] hashPubKey(ECPublicKey ecPublicKey) throws Exception{
        byte[] publicSHA256 = DataUtil.getSHA256Bytes(ecPublicKey.getEncoded());
        return DataUtil.encodeRipeMD160(publicSHA256);
    }
    private static byte[] checksum(byte[] payload){
        byte[] firstSHA = DataUtil.getSHA256Bytes(payload);
        byte[] secondSHA = DataUtil.getSHA256Bytes(firstSHA);
        byte[] retBytes = new byte[WalletConstant.ADDRESS_CHECKSUM_LEN];
        System.arraycopy(secondSHA, 0, retBytes, 0, 4);
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

    public static Wallet getWalletFromWallets(String from){
        //TO-DO //钱包存文件
        return Wallets.getWallets().get("from");
    }
}
