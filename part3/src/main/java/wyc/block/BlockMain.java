package wyc.block;

import org.apache.log4j.Logger;
import wyc.block.util.BlockChainUtil;

public class BlockMain {

	private static Logger logger = Logger.getLogger(BlockMain.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockChainUtil.printChain();
		BlockChainUtil.addBlock("Send 3 BTC to Ivan");
		BlockChainUtil.printChain();

	}
}
