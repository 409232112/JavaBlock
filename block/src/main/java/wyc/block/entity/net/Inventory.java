package wyc.block.entity.net;

import java.io.Serializable;
import java.util.List;

public class Inventory implements Serializable {
    private String addrFrom;
    private String type;
    private List<byte[]> items;

    public Inventory(String addrFrom,String type,List<byte[]> items){
        this.addrFrom=addrFrom;
        this.type=type;
        this.items=items;
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

    public List<byte[]> getItems() {
        return items;
    }

    public void setItems(List<byte[]> items) {
        this.items = items;
    }
}
