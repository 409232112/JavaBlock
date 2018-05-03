package wyc.block;

import org.apache.log4j.Logger;
import wyc.block.constant.BlockConstant;
import wyc.block.constant.WalletConstant;
import wyc.block.entity.TxOutput;
import wyc.block.util.DataUtil;
import wyc.block.util.RedisUtil;
import wyc.block.util.blockchain.BlockChainUtil;
import wyc.block.util.blockchain.WalletUtil;
import wyc.block.util.encrypt.SerializeUtil;
import wyc.block.util.transacation.TransactionUtil;
import wyc.block.util.transacation.UTXOUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BlockMain {

	private static Logger logger = Logger.getLogger(BlockMain.class);

    private static final  String walletFilePath = WalletConstant.WALLET_FILE_DIR+WalletConstant.WALLET_FILE;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws  Exception{

		String address1 = WalletUtil.createWallet();
		String address2 = WalletUtil.createWallet();
		//String address3 = WalletUtil.createWallet();

	//	System.out.println("address1:"+address1);
	//	System.out.println("address2:"+address2);
	//	System.out.println("address3:"+address3);
		BlockChainUtil.createBlockChain(address1);
	//	BlockChainUtil.printChain();
		TransactionUtil.send(address1,address2,6);
	//	TransactionUtil.send("16YqwiNnBUFQt2DAhTV9GHRduZQUnjZg15","13jqi39WXxh1KULsqoJCaLJfsUD9GygPsS",4);

	//	TransactionUtil.getBalance("1KLxKV3eCNuBTNLSm6JkhKQRNuCBxbSkUw");


		WalletUtil.printWallet();
		BlockChainUtil.printChain();






	}

}
