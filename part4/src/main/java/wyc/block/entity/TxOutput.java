package wyc.block.entity;

import java.io.Serializable;

/**
 * 输出
 * value: 有多少币，就是存储在 Value 里面
 * scriptPubKey: 对输出进行锁定
 */
public class TxOutput implements Serializable {

    private int value;
    private String scriptPubKey;

    public TxOutput(int value , String scriptPubKey){
        this.value = value;
        this.scriptPubKey=scriptPubKey;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(String scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }
    public boolean canBeUnlockedWith(String unlockingData ){
        return  getScriptPubKey().equals(unlockingData) ;
    }


}
