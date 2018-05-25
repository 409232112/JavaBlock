package wyc.block.entity.net;

import java.io.Serializable;

public class Version implements Serializable {

    private int version;
    private int bestHeight;
    private String ipFrom;

    public Version(int version,int bestHeight,String addrFrom ){
        this.version=version;
        this.bestHeight=bestHeight;
        this.ipFrom=addrFrom;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getBestHeight() {
        return bestHeight;
    }

    public void setBestHeight(int bestHeight) {
        this.bestHeight = bestHeight;
    }

    public String getIpFrom() {
        return ipFrom;
    }

    public void setIpFrom(String ipFrom) {
        this.ipFrom = ipFrom;
    }
}
