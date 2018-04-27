package wyc.block;

import org.apache.log4j.Logger;
import wyc.block.constant.WalletConstant;
import wyc.block.entity.Wallet;
import wyc.block.util.blockchain.BlockChainUtil;
import wyc.block.util.blockchain.WalletUtil;
import wyc.block.util.transacation.TransactionUtil;

public class BlockMain {

	private static Logger logger = Logger.getLogger(BlockMain.class);

    private static final  String walletFilePath = WalletConstant.WALLET_FILE_DIR+WalletConstant.WALLET_FILE;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws  Exception{

		TransactionUtil.send("1PuTQDHDgRzGt2kpysdZ8cmpqSofUC8d3w","1CrVmnEtiPUvVKWEhsHAx7e8ATLSajSYYY",3);
		WalletUtil.printWallet();
	}

}
