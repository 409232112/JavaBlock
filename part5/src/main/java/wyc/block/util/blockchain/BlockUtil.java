package wyc.block.util.blockchain;

import wyc.block.entity.Block;
import wyc.block.entity.ProofOfWork;
import wyc.block.entity.Transaction;
import wyc.block.util.DataUtil;
import wyc.block.util.encrypt.SerializeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockUtil {
	/**
	 * 创建新区块
	 * @param txs 	交易
	 * @param prevBlockHash 	前一个区块hash值
	 * @return
	 */
	public static Block getNewBlock(List<Transaction> txs, byte[] prevBlockHash) {
		Block block = new Block(prevBlockHash, txs);
		ProofOfWork proofOfWork = ProofOfWorkUtil.getNewProofOfWork(block);
		Map proofData = ProofOfWorkUtil.run(proofOfWork);
		block.setHash((byte[])proofData.get("hash"));
		block.setNonce((Integer) proofData.get("nonce"));
		return block;
	}

	/**
	 * 创建创世区块，就是第一个区块
	 * @return
	 */
	public static Block getNewGenesisBlock(Transaction coninbase) {
		List<Transaction> txs = new ArrayList<Transaction>();
		txs.add(coninbase);
		return getNewBlock(txs, new byte[0]);
	}

	public static byte[] getTransactionsHash(Block block){
		List<Transaction> txs = block.getTransactions();
		byte[][] txHashes = new byte[txs.size()][];
		for(int i=0;i<txs.size();i++){
			txHashes[i] = txs.get(i).getId();
		}
		byte[] txHash = DataUtil.joinByte(txHashes);
		return DataUtil.getSHA256Bytes(txHash);

	}

	/**
	 * block 对象序列化
	 * @param block
	 * @return
	 */
	public static byte[] serialize(Block block){
		return SerializeUtil.serialize(block);
	}

	/**
	 * block 对象反序列化
	 * @param bytes
	 * @return
	 */
	public static Block deserialize(byte[] bytes){
		Object o =SerializeUtil.deserialize(bytes);
		return o==null?null:(Block) o;
	}
}
