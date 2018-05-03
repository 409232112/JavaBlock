package wyc.block.util.blockchain;

import wyc.block.entity.MerkleNode;
import wyc.block.entity.MerkleTree;
import wyc.block.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class MerkleUtil {

    public static MerkleNode getNewMerkleNode(MerkleNode left,MerkleNode right,byte[] data){
        MerkleNode merkleNode = new MerkleNode();
        byte[] hash;
        if(left == null && right == null){
            hash=DataUtil.getSHA256Bytes(data);
        }else{
            byte[] prevHashes =  DataUtil.combineBytes(left.getData(),right.getData());
            hash=DataUtil.getSHA256Bytes(prevHashes);
        }
        merkleNode.setLeft(left);
        merkleNode.setRight(right);
        merkleNode.setData(hash);
        return merkleNode;
    }

    public static MerkleTree getNewMerkleTree(List<byte[]> datas){
        List<MerkleNode> nodes  =  new ArrayList<MerkleNode>();
        if(datas.size()%2 != 0){
            datas.add(datas.get(datas.size()-1));
        }
        for(byte[] datum : datas){
            MerkleNode node = getNewMerkleNode(null,null,datum);
            nodes .add(node);
        }
        for(int i=0;i<datas.size()/2;i++){
            List<MerkleNode> newLevel  =  new ArrayList<MerkleNode>();
            for(int j=0;j<nodes .size();j+=2){
                MerkleNode node =  getNewMerkleNode(nodes.get(j),nodes.get(j+1),null);
                newLevel.add(node);
            }
            nodes = newLevel;
        }
        return new MerkleTree(nodes.get(0));
    }
}
