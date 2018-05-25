package wyc.block.entity.net;

import wyc.block.entity.Block;

import java.io.Serializable;

public class NetBlock implements Serializable {
    private String addrFrom;
    private Block block;
    public NetBlock(String addrFrom,Block block){
        this.addrFrom=addrFrom;
        this.block=block;
    }
    public String getAddrFrom() {
        return addrFrom;
    }

    public void setAddrFrom(String addrFrom) {
        this.addrFrom = addrFrom;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
