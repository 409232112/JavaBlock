package wyc.block.util;

import wyc.block.entity.Transaction;
import wyc.block.entity.TxInput;
import wyc.block.entity.TxOutPut;

import java.util.ArrayList;
import java.util.List;

public class TransactionUtil {
    private static final int subsidy = 10;

    /**
     * 判断是否是 coinbase 交易
     * @param tx
     * @return
     */
    public boolean isCoinbase(Transaction tx){
        return tx.getvIns().size()==1 && tx.getvIns().get(0).getTxId().length==0 && tx.getvIns().get(0).getVout()==-1;
    }

    /**
     *  获取一个 coinbase 交易，该没有输入，只有一个输出
     * @param data
     * @param to
     * @return
     */
    public Transaction getNewCoinbaseTx(String data,String to){
        if(data ==""){
            data=to;
        }
        List vIns = new ArrayList<TxInput>();
        vIns.add(new TxInput(new byte[0],-1,data));

        List vOuts = new ArrayList<TxOutPut>();
        vOuts.add( new TxOutPut(subsidy,to));

        return new  Transaction(vIns,vOuts);
    }


}
