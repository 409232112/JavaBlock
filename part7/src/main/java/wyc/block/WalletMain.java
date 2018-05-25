package wyc.block;

import wyc.block.util.blockchain.WalletUtil;
import wyc.block.util.transacation.TransactionUtil;

public class WalletMain {
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



        //2
        TransactionUtil.getBalance("1Eejgnmnkp72bnya8ZtLmKnVf5vsS1KXhm");
        TransactionUtil.getBalance("12Rm812Yw8QAUyN9TpB74F9x9NcXG9ex84");
        TransactionUtil.getBalance("1MsVzsmBgb9PistevJrbNfxxB25i6FbY5p");



    }
}
