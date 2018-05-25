package wyc.block.entity.net;

import wyc.block.entity.Transaction;

import java.io.Serializable;

public class Tx implements Serializable {

    private String addrFrom;
    private Transaction transaction;

    public Tx(String addrFrom,Transaction transaction){
        this.addrFrom=addrFrom;
        this.transaction=transaction;
    }


    public String getAddrFrom() {
        return addrFrom;
    }

    public void setAddrFrom(String addrFrom) {
        this.addrFrom = addrFrom;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }


}
