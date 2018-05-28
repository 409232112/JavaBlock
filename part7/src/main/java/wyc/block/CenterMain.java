package wyc.block;

import wyc.block.constant.BlockConstant;
import wyc.block.entity.Block;
import wyc.block.entity.Transaction;
import wyc.block.entity.TxInput;
import wyc.block.entity.TxOutput;
import wyc.block.entity.net.NetBlock;
import wyc.block.net.Client;
import wyc.block.util.DataUtil;
import wyc.block.util.ServerUtil;
import wyc.block.util.blockchain.BlockChainUtil;
import wyc.block.util.blockchain.WalletUtil;
import wyc.block.util.encrypt.SerializeUtil;
import wyc.block.util.transacation.TransactionUtil;
import wyc.block.util.transacation.UTXOUtil;

import java.util.ArrayList;
import java.util.List;

public class CenterMain {
    public static void main(String[] args) throws  Exception{


        /*
        String address = WalletUtil.createWallet();
        System.out.println(address);
        BlockChainUtil.createBlockChain(address);
        */

        /*
        TransactionUtil.send("13ifq7BfguxhKmrwmkRVvz3qQD8qNnaZNq","1HcvgtJbbU2sYuRNRHAWfLNK72iiDY3W9N",3,true);
        TransactionUtil.send("13ifq7BfguxhKmrwmkRVvz3qQD8qNnaZNq","1HAzPgj1iwaFqjdF9iwBtgPMdgSLTQGqsC",4,true);
        */




        TransactionUtil.getBalance("13ifq7BfguxhKmrwmkRVvz3qQD8qNnaZNq");

        TransactionUtil.getBalance("1HcvgtJbbU2sYuRNRHAWfLNK72iiDY3W9N");
        TransactionUtil.getBalance("1HAzPgj1iwaFqjdF9iwBtgPMdgSLTQGqsC");
        TransactionUtil.getBalance("13NWKLzhfrNh1t8Qxr95iA8bHhg6AgxnsG");

        TransactionUtil.getBalance("122nWkQTRpXLiyctAtGTU2Aixii5bNLP5X");


        WalletUtil.printWallet();





    }
}
