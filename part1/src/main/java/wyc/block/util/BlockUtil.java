package wyc.block.util;

import wyc.block.entity.Block;

import java.util.Date;




public class BlockUtil {
	/**
	 * 创建新区块
	 * @param data 	数据
	 * @param prevBlockHash 	前一个区块hash值
	 * @return
	 */
	public static Block getNewBlock(byte[] data, byte[] prevBlockHash) {
		Block block = new Block(new Date().getTime(), prevBlockHash, data);
		setHash(block);
		return block;
	}

	/**
	 * 根据PrevBlockHash + Data + Timestamp 设置当前区块Hash值
	 * @param block
	 */
	public static void setHash(Block block) {
		byte[] header = DataUtil.joinByte(block.getPrevBlockHash(),block.getData(),DataUtil.longToByteArray(block.getTimestamp()));
		block.setHash(DataUtil.getSHA256Bytes(header));
	}

	/**
	 * 创建创世区块，就是第一个区块
	 * @return
	 */
	public static Block getNewGenesisBlock() {
		return getNewBlock(DataUtil.string2Bytes("Genesis Block"), new byte[0]);
	}
}
