package wyc.block.entity;


public class MerkleTree {
    private MerkleNode rootNode;

    public MerkleTree(MerkleNode rootNode){
        this.rootNode = rootNode;
    }
    public MerkleNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(MerkleNode rootNode) {
        this.rootNode = rootNode;
    }


}
