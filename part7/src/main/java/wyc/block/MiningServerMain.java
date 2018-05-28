package wyc.block;

import wyc.block.util.ServerUtil;
import wyc.block.util.blockchain.WalletUtil;

public class MiningServerMain {
    public static void main(String[] args) throws  Exception{
        /*
        String address = WalletUtil.createWallet();
        System.out.println("MINER_WALLET:"+address);
        */
        ServerUtil.startServer("122nWkQTRpXLiyctAtGTU2Aixii5bNLP5X");

    }
}
