package wyc.block.entity;

import wyc.block.util.blockchain.WalletUtil;

import java.io.Serializable;
import java.security.interfaces.ECPublicKey;
import java.util.Arrays;

/**
 * 输出
 * txId: 一个交易输入引用了之前一笔交易的一个输出, ID 表明是之前哪笔交易
 * vout: 一笔交易可能有多个输出，vout 为输出的索引
 * scripSig: 提供解锁输出 txId:vout 的数据
 */
public class TxInput implements Serializable {

    private byte[] txId;//交易ID
    private int vout;//输出数量
    private byte[] signature;
    private static ECPublicKey publicKey;

    public TxInput(byte[] txId,int vout,byte[] signature,ECPublicKey publicKey){
        this.txId=txId;
        this.vout=vout;
        this.signature=signature;
        this.publicKey=publicKey;
    }

    public byte[] getTxId() { return txId; }

    public void setTxId(byte[] txId) {
        this.txId = txId;
    }

    public int getVout() {
        return vout;
    }

    public void setVout(int vout) {
        this.vout = vout;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public static ECPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(ECPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public boolean useKey(byte[] pubKeyHash) throws Exception{
        byte[] lockingHash = WalletUtil.hashPubKey(getPublicKey());
        return Arrays.equals(lockingHash,pubKeyHash);
    }

}
