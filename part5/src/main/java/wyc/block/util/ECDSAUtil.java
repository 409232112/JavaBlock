package wyc.block.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ECDSAUtil {
    private static KeyPair keyPair;
    private static  KeyFactory keyFactory;
    static{
        //1.初始化密钥
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            keyPair = keyPairGenerator.generateKeyPair();
            keyFactory = KeyFactory.getInstance("EC");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static ECPrivateKey getPrivateKey(){
        return (ECPrivateKey)keyPair.getPrivate();
    }
    public static ECPublicKey getPublicKey(){
        return (ECPublicKey)keyPair.getPublic();
    }

    /**
     * 对数据进行签名
     * @param src 源数据
     * @return
     * @throws Exception
     */
    public static String doSign(String src) throws  Exception{
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(getPrivateKey().getEncoded());
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1withECDSA");
        signature.initSign(privateKey);
        signature.update(src.getBytes());
        byte[] res = signature.sign();
        return HexBin.encode(res);
    }

    /**
     * 验证
     * @param src  源数据
     * @param sign 签名后数据
     * @return
     * @throws Exception
     */
    public static boolean validate(String src,String sign) throws  Exception{
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(getPublicKey().getEncoded());
        keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1withECDSA");
        signature.initVerify(publicKey);
        signature.update(src.getBytes());
        return signature.verify(HexBin.decode(sign));
    }

    public static void main(String[] args) throws  Exception{
        String src = "ecdsa security";
        String result = doSign(src);
        System.out.println(doSign(src));
        System.out.println(validate(src,result));
    }

}
