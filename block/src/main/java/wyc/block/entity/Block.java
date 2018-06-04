package wyc.block.entity;

import wyc.block.util.DataUtil;
import wyc.block.util.blockchain.WalletUtil;
import wyc.block.util.transacation.TransactionUtil;

import java.io.Serializable;
import java.util.*;

/**
 * Block实体由区块头和交易两部分构成
 * Timestamp, PrevBlockHash, Hash 属于区块头（block header）
 * Timestamp     : 当前时间戳，也就是区块创建的时间
 * PrevBlockHash : 前一个块的哈希
 * Hash          : 当前块的哈希
 * transactions  : 区块实际存储交易
 * nonce         : 碰撞因子
 * height        :块高度
 * @author Administrator
 */
public class Block implements Serializable {
	
	private long timestamp;
	private byte[] prevBlockHash;
	private byte[] hash;
	private List<Transaction> transactions;
	private int nonce;
	private int height;
	
	public Block(byte[] prevBlockHash,  List<Transaction> transactions,int height){
		this.timestamp=new Date().getTime();
		this.prevBlockHash=prevBlockHash;
		this.transactions=transactions;
		this.nonce=0;
		this.height=height;
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
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	public void setHash(byte[] hash) {
		this.hash = hash;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	@Override
	public String toString() {
		String TransactionsInfo="";
		try{
			for(Transaction tx : getTransactions()){
				TransactionsInfo += "---- Transaction "+DataUtil.byte2Hex(tx.getId())+":\n";
				TransactionsInfo += "          IsCoinbase : "+TransactionUtil.isCoinbase(tx);

				List<Map> txInputList = new ArrayList();
				Map txInputMap = new HashMap();
				for(TxInput txInput :tx.getvIns() ){
					txInputMap.put("TxId",	DataUtil.byte2Hex(txInput.getTxId()));
					txInputMap.put("Vout",	txInput.getVout());
					txInputMap.put("PubKeyHash", DataUtil.byte2Hex(WalletUtil.hashPubKey(txInput.getPubKey())));
					txInputMap.put("Signature",	DataUtil.bytes2String(txInput.getSignature()));
					txInputList.add(txInputMap);
					txInputMap = new HashMap();
				}
				TransactionsInfo += "\n              TxInput : " +txInputList;

				List<Map> txOutputList = new ArrayList();
				Map txOutputMap = new HashMap();
				for(TxOutput txOutput:tx.getvOuts()){
					txOutputMap.put("Value",txOutput.getValue());
					txOutputMap.put("PubKeyHash",DataUtil.byte2Hex(txOutput.getPubKeyHash()));
					txOutputList.add(txOutputMap);
					txOutputMap = new HashMap();
				}
				TransactionsInfo += "\n              TxOutput : " +txOutputList+"\n";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "===================== Block "+DataUtil.byte2Hex(getHash())+" =====================\n"+
				"Prev Block:"+DataUtil.byte2Hex(getPrevBlockHash())+"\n"+
				"Nonce:"+getNonce()+"\n"+
				TransactionsInfo;
	}
}
