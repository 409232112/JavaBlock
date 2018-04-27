package wyc.block.entity;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class ECDSAKey implements Serializable {

    private ECPrivateKey privateKey;
    private ECPublicKey publicKey;

    public ECDSAKey(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.privateKey = (ECPrivateKey)keyPair.getPrivate();
            this.publicKey = (ECPublicKey) keyPair.getPublic();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public  ECPrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public  ECPublicKey getPublicKey() {
        return this.publicKey;
    }



}
