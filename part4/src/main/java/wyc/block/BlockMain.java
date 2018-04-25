package wyc.block;

import org.apache.log4j.Logger;
import wyc.block.util.BlockChainUtil;
import wyc.block.util.DataUtil;
import wyc.block.util.TransactionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockMain {

	private static Logger logger = Logger.getLogger(BlockMain.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	//	BlockChainUtil.createBlockChain("wyc");//创建一个区块链，默认每块10奖励
	//	BlockChainUtil.printChain();//打印区块链数据
		TransactionUtil.send("wyc","cj",100);//wyc 向 cj 转账2
		BlockChainUtil.printChain();
		TransactionUtil.getBalance("cj");//获取cj地址余额
		TransactionUtil.getBalance("wyc");//获取wyc地址余额
	}
}
