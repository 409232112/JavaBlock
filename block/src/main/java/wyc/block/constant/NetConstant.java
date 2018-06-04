package wyc.block.constant;

import wyc.block.util.PropertiesUtil;

public class NetConstant {
    public static final String centorIP;
    public static final int commonPort;
    public static final int packageCount;
    static{
        PropertiesUtil propertiesUtil=new PropertiesUtil("application.properties");
        centorIP=propertiesUtil.getPropertyValue("net.center.ip");
        commonPort=Integer.valueOf(propertiesUtil.getPropertyValue("net.common.port"));
        packageCount=Integer.valueOf(propertiesUtil.getPropertyValue("block.mining.package"));
    }
}
