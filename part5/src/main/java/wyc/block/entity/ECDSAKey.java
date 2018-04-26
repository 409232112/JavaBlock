package wyc.block.entity;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class ECDSAKey {

    private static ECPrivateKey privateKey;
    private static ECPublicKey publicKey;

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

    public static ECPrivateKey getPrivateKey() {
        return privateKey;
    }

    public static ECPublicKey getPublicKey() {
        return publicKey;
    }



}
