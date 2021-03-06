package wyc.block.util.encrypt;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ECDSAUtil {

    private static KeyFactory keyFactory;
    private static Signature signature;

    static{
        init();
    }
    private static void init(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            keyFactory = KeyFactory.getInstance("EC");
            signature =Signature.getInstance("SHA1withECDSA");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 对数据进行签名
     * @param src 源数据
     * @return
     * @throws Exception
     */
    public static String doSignAsString(String src,ECPrivateKey ecPrivateKey) throws  Exception{
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        signature.initSign(privateKey);
        signature.update(src.getBytes());
        byte[] res = signature.sign();
        return HexBin.encode(res);
    }
    public static byte[] doSignAsBytes(String src,ECPrivateKey ecPrivateKey) throws  Exception{
        return doSignAsString(src,ecPrivateKey).getBytes();
    }


    /**
     * 验证
     * @param src  源数据
     * @param sign 签名后数据
     * @return
     * @throws Exception
     */
    public static boolean validate(String src,String sign,ECPublicKey ecPublicKey) throws  Exception{
       return validateBytesKey(src,sign,ecPublicKey.getEncoded());
    }
    public static boolean validateBytesKey(String src,String sign,byte[] pubKey)  throws  Exception{
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        signature.initVerify(publicKey);
        signature.update(src.getBytes());
        return signature.verify(HexBin.decode(sign));
    }
}
