package wyc.block.entity.net;

import java.io.Serializable;
import java.util.List;

public class Address  implements Serializable {

    private List<String> address;
    public Address(List<String> address){
        this.address=address;
    }
    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

}
