package wyc.block.entity;

import wyc.block.util.DataUtil;

/**
 * Block实体由区块头和交易两部分构成
 * Timestamp, PrevBlockHash, Hash 属于区块头（block header）
 * Timestamp     : 当前时间戳，也就是区块创建的时间
 * PrevBlockHash : 前一个块的哈希
 * Hash          : 当前块的哈希
 * Data          : 区块实际存储的信息，比特币中也就是交易
 * @author Administrator
 */
public class Block {
	
	private long timestamp;
	private byte[] prevBlockHash;
	private byte[] hash;
	private byte[] data;
	
	public Block(long timestamp, byte[] prevBlockHash,  byte[] data){
		this.timestamp=timestamp;
		this.prevBlockHash=prevBlockHash;
		this.data=data;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public byte[] getPrevBlockHash() {
		return prevBlockHash;
	}
	
	public byte[] getHash() {
		return hash;
	}
	
	public byte[] getData() {
		return data;
	}
	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	@Override
	public String toString() {
		return "Prev Hash:"+DataUtil.byte2Hex(getPrevBlockHash())+"\n"+
			   "Data:"+DataUtil.bytes2String(getData())+"\n"+
				"Hash:"+DataUtil.byte2Hex(getHash())+"\n";
	}
}
