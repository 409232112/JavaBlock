package wyc.block.entity;

import wyc.block.util.DataUtil;
import wyc.block.util.SerializeUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 交易实体
 * Transaction 由交易 ID，输入和输出构成
 */
public class Transaction implements Serializable {

    private byte[] id;
    private List<TxInput> vIns;
    private List<TxOutput> vOuts;

    public Transaction(List<TxInput> vIns,List<TxOutput> vOuts){
        this.vIns = vIns;
        this.vOuts =vOuts;
        setId();
    }

    public byte[] getId() {
        return id;
    }

    private void setId() {
        this.id = DataUtil.getSHA256Bytes(SerializeUtil.serialize(this.getClass()));
    }

    public  List<TxInput> getvIns() {
        return vIns;
    }

    public void setvIns( List<TxInput> vIns) {
        this.vIns = vIns;
    }

    public  List<TxOutput> getvOuts() {
        return vOuts;
    }

    public void setvOuts( List<TxOutput> vOuts) {
        this.vOuts = vOuts;
    }


}
