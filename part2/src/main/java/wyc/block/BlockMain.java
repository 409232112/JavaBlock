package wyc.block;

import wyc.block.entity.BlockChain;

public class BlockMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockChain bc = new BlockChain();
		bc.addBlock("Send 1 BTC to Ivan");
		bc.addBlock("Send 2 more BTC to Ivan");
		bc.addBlock("Send 3 BTC to Ivan");
		bc.print();

	}

}
