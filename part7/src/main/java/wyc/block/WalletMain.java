package wyc.block;

import wyc.block.util.blockchain.BlockChainUtil;
import wyc.block.util.blockchain.WalletUtil;
import wyc.block.util.transacation.TransactionUtil;

import java.util.ArrayList;
import java.util.List;

public class WalletMain {
    private  static int result  =1;
    public  static void main(String[] args) throws  Exception{
        /*
         //1、生成一些地址
        String address1 = WalletUtil.createWallet();
        String address2 = WalletUtil.createWallet();
        String address3 = WalletUtil.createWallet();
        System.out.println(address1);
        System.out.println(address2);
        System.out.println(address3);
        */


        TransactionUtil.send("1HcvgtJbbU2sYuRNRHAWfLNK72iiDY3W9N","13NWKLzhfrNh1t8Qxr95iA8bHhg6AgxnsG",1,false);
        TransactionUtil.send("1HAzPgj1iwaFqjdF9iwBtgPMdgSLTQGqsC","13NWKLzhfrNh1t8Qxr95iA8bHhg6AgxnsG",1,false);



        /*
        TransactionUtil.getBalance("13ifq7BfguxhKmrwmkRVvz3qQD8qNnaZNq");

        TransactionUtil.getBalance("1HcvgtJbbU2sYuRNRHAWfLNK72iiDY3W9N");
        TransactionUtil.getBalance("1HAzPgj1iwaFqjdF9iwBtgPMdgSLTQGqsC");
        TransactionUtil.getBalance("13NWKLzhfrNh1t8Qxr95iA8bHhg6AgxnsG");

        TransactionUtil.getBalance("122nWkQTRpXLiyctAtGTU2Aixii5bNLP5X");
        */
    }
}
