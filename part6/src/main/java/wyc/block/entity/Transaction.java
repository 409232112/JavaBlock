package wyc.block.entity;

import wyc.block.util.DataUtil;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 交易实体
 * Transaction 由交易 ID，输入和输出构成
 */
public class Transaction implements Serializable {

    private byte[] id;
    private List<TxInput> vIns;
    private List<TxOutput> vOuts;

    public Transaction(List<TxInput> vIns, List<TxOutput> vOuts){
        this.vIns = vIns;
        this.vOuts =vOuts;
        setId();
    }
    public Transaction(byte[] txId, List<TxInput> vIns, List<TxOutput> vOuts){
        this.id = txId;
        this.vIns = vIns;
        this.vOuts =vOuts;

    }
    public byte[] getId() {
        return id;
    }

    private void setId() {
        this.id = DataUtil.getSHA256Bytes(UUID.randomUUID().toString().getBytes());
        System.out.println();
    }

    public void setId(byte[] id ){
        this.id=id;
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
