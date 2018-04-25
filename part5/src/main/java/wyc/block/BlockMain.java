package wyc.block;

import com.chrylis.codec.base58.Base58Codec;
import org.apache.log4j.Logger;
import wyc.block.util.Base58Util;
import wyc.block.util.BlockChainUtil;
import wyc.block.util.DataUtil;
import wyc.block.util.TransactionUtil;

public class BlockMain {

	private static Logger logger = Logger.getLogger(BlockMain.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] a ={1,1,1,1,1,1,1,1,1,1};

		System.out.println(Base58Codec.doEncode(a));
 		byte[] b = Base58Codec.doDecode(Base58Codec.doEncode(a));
 		for(int i=0;i<b.length;i++){
 			System.out.println(b[i]);
		}
		System.out.println(Base58Util.encode(a));
		byte[] c= Base58Util.decode(Base58Util.encode(a));
		for(int i=0;i<b.length;i++){
			System.out.println(b[i]);
		}

	}
}
