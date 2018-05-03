package wyc.block.entity;

import java.io.Serializable;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class Wallet implements Serializable {

    private ECPrivateKey privateKey;
    private ECPublicKey publicKey;

    public Wallet(){
        ECDSAKey ecdsaKey = new ECDSAKey();
        this.privateKey=ecdsaKey.getPrivateKey();
        this.publicKey=ecdsaKey.getPublicKey();

    }

    public  ECPrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(ECPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public ECPublicKey getPublicKey() {
        return this.publicKey;
    }
    public  void setPublicKey(ECPublicKey publicKey) {
        this.publicKey = publicKey;
    }
}