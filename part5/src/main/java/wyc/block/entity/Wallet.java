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
    }

    public static ECPrivateKey getPrivateKey() {
        return privateKey;
    }

    public static void setPrivateKey(ECPrivateKey privateKey) {
        Wallet.privateKey = privateKey;
    }

    public static ECPublicKey getPublicKey() {
        return publicKey;
    }

    public static void setPublicKey(ECPublicKey publicKey) {
        Wallet.publicKey = publicKey;
    }
}