package wyc.block.util.transacation;

import wyc.block.constant.BlockConstant;
import wyc.block.entity.Block;
import wyc.block.entity.Transaction;
import wyc.block.entity.TxInput;
import wyc.block.entity.TxOutput;
import wyc.block.util.DataUtil;
import wyc.block.util.RedisUtil;
import wyc.block.util.blockchain.BlockChainUtil;
import wyc.block.util.encrypt.SerializeUtil;

import java.util.*;


public class UTXOUtil {
    public static void  reIndex(){
        RedisUtil.flush(BlockConstant.UTXO_INDEX);
        Map<String,List<TxOutput>> UTXO = BlockChainUtil.findUTXO();
        for (Map.Entry<String,List<TxOutput>> entry : UTXO.entrySet()) {
            RedisUtil.set(BlockConstant.UTXO_INDEX,
                    DataUtil.hexStringToBytes(entry.getKey()),
                    SerializeUtil.serialize(entry.getValue()));
        }
    }

    /**
     * 从UTXO集合中找到未花费输出
     * @param pubKeyHash
     * @return
     */
    public static List<TxOutput> findUTXO(byte[] pubKeyHash) {
        List<TxOutput> utxoList = new ArrayList<TxOutput>();
        Set<byte[]> keys = RedisUtil.keysByString(BlockConstant.UTXO_INDEX, "*");
        if (keys != null && keys.size() > 0) {
            for (byte[] key : keys) {
                List<TxOutput> txOutputs = (List<TxOutput>)SerializeUtil.deserialize(RedisUtil.get(BlockConstant.UTXO_INDEX,key));
                for(int outIdx=0;outIdx<txOutputs.size();outIdx++){
                    if(txOutputs.get(outIdx).isLockedWithKey(pubKeyHash)){
                        utxoList.add(txOutputs.get(outIdx));
                    }
                }
            }
        }
        return utxoList;
    }

    /**
     * 从utxo集合中找到至少有 amount 的 UTXO
     * @param pubKeyHash
     * @param amount
     * @return
     */
    public static Map findSpendableOutputs(byte[] pubKeyHash,int amount) {
        Map retMap = new HashMap();
        Map<String ,List<Integer>> unspentOutputs = new HashMap<String ,List<Integer>>();
        int accumulated =0;
        Set<byte[]> keys = RedisUtil.keysByString(BlockConstant.UTXO_INDEX,"*");
        if(keys != null && keys.size()>0){
            for(byte[] key:keys){
                List<TxOutput> txOutputs = (List<TxOutput>)SerializeUtil.deserialize(RedisUtil.get(BlockConstant.UTXO_INDEX,key));
                String txId = DataUtil.byte2Hex(key);
                if(unspentOutputs.get(txId)==null){
                    unspentOutputs.put(txId,new ArrayList<Integer>());
                }
                for(int outIdx=0;outIdx<txOutputs.size();outIdx++){
                    if(txOutputs.get(outIdx).isLockedWithKey(pubKeyHash) && accumulated<amount){
                        accumulated+=txOutputs.get(outIdx).getValue();
                        unspentOutputs.get(txId).add(outIdx);
                    }
                }
            }
        }
        retMap.put("accumulated",accumulated);
        retMap.put("unspentOutputs",unspentOutputs);
        return retMap;
    }

    public static void updateUTXO(Block block){
        for(Transaction tx : block.getTransactions()){
            if(!TransactionUtil.isCoinbase(tx)){
                for(TxInput txInput :tx.getvIns()){
                    List<TxOutput> updateTxOutputs = new ArrayList<TxOutput>();
                    List<TxOutput> txOutputs = (List<TxOutput>)SerializeUtil.deserialize(RedisUtil.get(BlockConstant.UTXO_INDEX,txInput.getTxId()));
                    for(int outIdx=0;outIdx<txOutputs.size();outIdx++){
                        if(outIdx!=txInput.getVout()){
                            updateTxOutputs.add(txOutputs.get(outIdx));
                        }
                    }
                    if(updateTxOutputs.size()==0){
                        RedisUtil.del(BlockConstant.UTXO_INDEX,txInput.getTxId());
                    }else{
                        RedisUtil.set(BlockConstant.UTXO_INDEX,txInput.getTxId(),SerializeUtil.serialize(updateTxOutputs));
                    }
                }
            }
            List<TxOutput> newTxOutputs = new ArrayList<TxOutput>();
            for(TxOutput txOutput:tx.getvOuts()){
                newTxOutputs.add(txOutput);
            }
            RedisUtil.set(BlockConstant.UTXO_INDEX,tx.getId(),SerializeUtil.serialize(newTxOutputs));
        }
    }
}
