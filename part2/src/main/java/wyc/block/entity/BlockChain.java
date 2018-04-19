package wyc.block.entity;

import wyc.block.util.BlockUtil;
import wyc.block.util.DataUtil;
import wyc.block.util.ProofOfWorkUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 区块链实体
 * 由多个区块构成
 * @author Administrator
 *
 */
public class BlockChain {
	
	private List<Block> blocks;

	/**
	 * 初始化区块链，加入创世区块
	 */
	public BlockChain(){
		blocks= new ArrayList<Block>();
		blocks.add(BlockUtil.getNewGenesisBlock());
	}

	/**
	 * 向链中加入一个新块
	 * @param data 在实际中就是交易
	 */
	public void addBlock(String data){
		Block prevBlock = blocks.get(blocks.size()-1);
		Block newBlock =BlockUtil.getNewBlock(DataUtil.string2Bytes(data), prevBlock.getHash());
		blocks.add(newBlock);
	}

	/**
	 * 打印区块链中数据
	 */
	public void print() {
		for(int i=0;i<blocks.size();i++){
			System.out.print(blocks.get(i).toString());
			ProofOfWork proofOfWork = ProofOfWorkUtil.getNewProofOfWork(blocks.get(i));
			System.out.println("PoW "+ ProofOfWorkUtil.validate(proofOfWork));
		}
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}
	
}
