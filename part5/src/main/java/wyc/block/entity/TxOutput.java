package wyc.block.entity;

import wyc.block.util.DataUtil;
import wyc.block.util.encrypt.Base58Util;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 输出
 * value: 有多少币，就是存储在 Value 里面
 * scriptPubKey: 对输出进行锁定
 */
public class TxOutput implements Serializable {

    private int value;   //入账数量
    private byte[] pubKeyHash;

    public TxOutput(int value,String address){
        this.value = value;
        lock(DataUtil.string2Bytes(address));
    }
    public TxOutput(int value,byte[] pubKeyHash){
        this.value = value;
        this.pubKeyHash=pubKeyHash;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public byte[] getPubKeyHash() {
        return pubKeyHash;
    }

    public void setPubKeyHash(byte[] pubKeyHash) {
        this.pubKeyHash = pubKeyHash;
    }

    public void lock(byte[] address){
        byte[] bytes = Base58Util.decode(address);
        byte[] pubKeyHash = DataUtil.subBytes(bytes,1,bytes.length-4-1);
        setPubKeyHash(pubKeyHash);
    }

    public boolean isLockedWithKey(byte[] pubKeyHash){
        return Arrays.equals(getPubKeyHash(),pubKeyHash);
    }

}
