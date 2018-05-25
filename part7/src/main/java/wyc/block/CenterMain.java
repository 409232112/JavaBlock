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
        TransactionUtil.send("1Eejgnmnkp72bnya8ZtLmKnVf5vsS1KXhm","12Rm812Yw8QAUyN9TpB74F9x9NcXG9ex84",3,true);
        TransactionUtil.send("1Eejgnmnkp72bnya8ZtLmKnVf5vsS1KXhm","1MsVzsmBgb9PistevJrbNfxxB25i6FbY5p",4,true);
           */


        TransactionUtil.getBalance("1Eejgnmnkp72bnya8ZtLmKnVf5vsS1KXhm");
        TransactionUtil.getBalance("12Rm812Yw8QAUyN9TpB74F9x9NcXG9ex84");
        TransactionUtil.getBalance("1MsVzsmBgb9PistevJrbNfxxB25i6FbY5p");







    }
}
