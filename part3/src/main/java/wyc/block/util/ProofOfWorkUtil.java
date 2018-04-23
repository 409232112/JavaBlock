package wyc.block.util;

import org.apache.log4j.Logger;
import wyc.block.constant.ProofOfWorkConstant;
import wyc.block.entity.Block;
import wyc.block.entity.ProofOfWork;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ProofOfWorkUtil {

    private static Logger logger = Logger.getLogger(ProofOfWorkUtil.class);

    public static ProofOfWork getNewProofOfWork(Block block){
        BigInteger target =  DataUtil.moveLeft(1,(256-ProofOfWorkConstant.targetBits));
        return new ProofOfWork(block,target);
    }

    public static Map run(ProofOfWork proofOfWork){
        Map retMap = new HashMap();
        byte[] hash;
        System.out.println("Mining the block containing "+DataUtil.bytes2String(proofOfWork.getBlock().getData()));
        for(int nonce=0;nonce<ProofOfWorkConstant.maxNonce;nonce++){
            byte[] data = proofOfWork.prepareData(nonce);
            hash=DataUtil.getSHA256Bytes(data);
            BigInteger hashNum = DataUtil.bytes2BigInteger(hash);
            if(hashNum.compareTo(proofOfWork.getTarget())==-1 && hashNum.compareTo(new BigInteger("0"))==1){
                proofOfWork.getBlock().setNonce(nonce);
                retMap.put("hash",hash);
                retMap.put("nonce",nonce);
                break;
            }else{
                nonce++;
            }
        }
        return retMap;
    }

    /**
     * 校验工作量证明是否成立
     * @param proofOfWork
     * @return
     */
    public static boolean validate(ProofOfWork proofOfWork){
        byte[] data = proofOfWork.prepareData(proofOfWork.getBlock().getNonce());
        byte[] hash=DataUtil.getSHA256Bytes(data);
        BigInteger hashNum = DataUtil.bytes2BigInteger(hash);
        return   hashNum.compareTo(proofOfWork.getTarget())==-1;
    }
}
