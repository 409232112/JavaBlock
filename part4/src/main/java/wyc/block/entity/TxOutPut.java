package wyc.block.entity;

/**
 * 输出
 * value: 有多少币，就是存储在 Value 里面
 * scriptPubKey: 对输出进行锁定
 */
public class TxOutPut {

    private int value;
    private String scriptPubKey;

    public TxOutPut(int value ,String scriptPubKey){
        this.value = value;
        this.scriptPubKey=scriptPubKey;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(String scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }
    public boolean canBeUnlockedWith(String unlockingData ){
        return  getScriptPubKey() == unlockingData;
    }


}
