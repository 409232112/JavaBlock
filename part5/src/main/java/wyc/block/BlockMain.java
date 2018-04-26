package wyc.block;

import org.apache.log4j.Logger;
import wyc.block.entity.Wallet;
import wyc.block.util.DataUtil;
import wyc.block.util.WalletUtil;


public class BlockMain {

	private static Logger logger = Logger.getLogger(BlockMain.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) throws  Exception{
		Wallet wallet = new Wallet();
		System.out.println(DataUtil.bytes2String(WalletUtil.getAddress(wallet)));
	}

}
