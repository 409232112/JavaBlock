package wyc.block;

import org.apache.log4j.Logger;
import wyc.block.entity.Wallet;
import wyc.block.util.encrypt.Base58Util;
import wyc.block.util.DataUtil;
import wyc.block.util.blockchain.WalletUtil;
import wyc.block.util.transacation.TransactionUtil;


public class BlockMain {

	private static Logger logger = Logger.getLogger(BlockMain.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) throws  Exception{
		Wallet wallet = new Wallet();
		String a = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";


		TransactionUtil.getBalance(a);



	}

}
