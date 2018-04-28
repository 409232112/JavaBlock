package wyc.block.entity;

import wyc.block.constant.ProofOfWorkConstant;
import wyc.block.util.DataUtil;
import wyc.block.util.blockchain.BlockUtil;

import java.math.BigInteger;

public class ProofOfWork {

    private Block block;
    private BigInteger target;

    public ProofOfWork(Block block, BigInteger target) {
        this.block = block;
        this.target = target;
    }

    public Block getBlock() {
        return block;
    }

    public BigInteger getTarget() {
        return target;
    }

    /**
     * 根据计数器，组装数据
     * @param nonce
     * @return
     */
    public byte[] prepareData(int nonce) {
        return DataUtil.joinByte(block.getPrevBlockHash(),
                BlockUtil.getTransactionsHash(block),
                DataUtil.intToHex(block.getTimestamp()),
                DataUtil.intToHex(ProofOfWorkConstant.targetBits),
                DataUtil.intToHex(nonce));
    }

}
