package wyc.block.entity;

import java.io.Serializable;

/**
 * 输出
 * txId: 一个交易输入引用了之前一笔交易的一个输出, ID 表明是之前哪笔交易
 * vout: 一笔交易可能有多个输出，vout 为输出的索引
 * scripSig: 提供解锁输出 txId:vout 的数据
 */
public class TxInput implements Serializable {

    private byte[] txId;//交易ID
    private int vout;//输出数量
    private String scripSig;

    public TxInput(byte[] txId,int vout,String scripSig){
        this.txId=txId;
        this.vout=vout;
        this.scripSig=scripSig;
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

    public String getScripSig() {
        return scripSig;
    }

    public void setScripSig(String scripSig) {
        this.scripSig = scripSig;
    }

    public boolean canUnlockOutPutWith(String unlockingData ){
        return getScripSig().equals(unlockingData);
    }
}
