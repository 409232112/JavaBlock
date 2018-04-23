package wyc.block.entity;

import wyc.block.util.DataUtil;

import java.util.List;

/**
 * 交易实体
 * Transaction 由交易 ID，输入和输出构成
 */
public class Transaction {

    private byte[] id;
    private List<TxInput> vIns;
    private List<TxOutPut> vOuts;

    public Transaction(List<TxInput> vIns,List<TxOutPut> vOuts){
        this.vIns = vIns;
        this.vOuts =vOuts;
        setId();
    }

    public byte[] getId() {
        return id;
    }

    private void setId() {
        this.id = DataUtil.getSHA256Bytes(this.getClass().toString().getBytes());
    }

    public  List<TxInput> getvIns() {
        return vIns;
    }

    public void setvIns( List<TxInput> vIns) {
        this.vIns = vIns;
    }

    public  List<TxOutPut> getvOuts() {
        return vOuts;
    }

    public void setvOuts( List<TxOutPut> vOuts) {
        this.vOuts = vOuts;
    }


}
