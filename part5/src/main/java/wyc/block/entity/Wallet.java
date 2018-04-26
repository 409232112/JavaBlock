package wyc.block.entity;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class Wallet {

    private static ECPrivateKey privateKey;
    private static ECPublicKey publicKey;

    public Wallet(){
        ECDSAKey ecdsaKey = new ECDSAKey();
        this.privateKey=ecdsaKey.getPrivateKey();
        this.publicKey=ecdsaKey.getPublicKey();
        //to-do 每新建一个钱包实例，就应该把钱包存在文件里
    }

    public static ECPrivateKey getPrivateKey() {
        return privateKey;
    }

    public  void setPrivateKey(ECPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public static ECPublicKey getPublicKey() {
        return publicKey;
    }

    public  void setPublicKey(ECPublicKey publicKey) {
        this.publicKey = publicKey;
    }
}