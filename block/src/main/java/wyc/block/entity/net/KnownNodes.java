package wyc.block.entity.net;

import wyc.block.constant.NetConstant;

import java.util.ArrayList;
import java.util.List;

public class KnownNodes {

    private static List<String> knownNodes;

    static{
        knownNodes = new ArrayList<String>();
        knownNodes.add(NetConstant.centorIP);
    }
    public static List<String> getKnownNodes() {
        return knownNodes;
    }

    public static void setKnownNodes(List<String> knownNodes) {
        KnownNodes.knownNodes = knownNodes;
    }

    public static void addKnownNodes(String node){
        if(!knownNodes.contains(node))    {
            knownNodes.add(node);
        }
    }
}
