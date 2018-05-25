package wyc.block.entity.net;

import java.io.Serializable;

public class GetData implements Serializable {
    private String addrFrom;
    private String type;
    private byte[] id;

    public GetData(String addrFrom,String type,byte[] id){
        this.addrFrom=addrFrom;
        this.type=type;
        this.id=id;
    }

    public String getAddrFrom() {
        return addrFrom;
    }

    public void setAddrFrom(String addrFrom) {
        this.addrFrom = addrFrom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

}
