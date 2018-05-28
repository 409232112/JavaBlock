package wyc.block.util;

import org.apache.log4j.Logger;
import wyc.block.constant.BlockConstant;
import wyc.block.constant.NetConstant;
import wyc.block.entity.Block;
import wyc.block.entity.Transaction;
import wyc.block.entity.net.*;
import wyc.block.net.Client;
import wyc.block.net.Server;
import wyc.block.util.blockchain.BlockChainUtil;
import wyc.block.util.blockchain.WalletUtil;
import wyc.block.util.encrypt.SerializeUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerUtil {

    private static String nodeAddress;
    private static Map<String,Transaction> mempool;
    private static String miningAddress;
    private static Logger logger = Logger.getLogger(ServerUtil.class);

    static{
        mempool =new HashMap<String, Transaction>();
    }

    public static void startServer(String minerAddress){
        if(minerAddress!=null){
            if(WalletUtil.validateAddress(minerAddress)){
                logger.info("Mining is on. Address to receive rewards: "+minerAddress);
            }else{
                logger.info("Wrong miner address! "+minerAddress);
            }
        }
        nodeAddress = getLocalIP();
        miningAddress = minerAddress;
        new Server(NetConstant.commomPort).start();
        if(!KnownNodes.getKnownNodes().contains(nodeAddress)){
            sendVersion(NetConstant.centorIP);
        }
    }
    public static void sendData(String address,byte[] type,byte[] payload){
        byte[] data = DataUtil.combineBytes(type,payload);
        short length =  (short)data.length;
        data = DataUtil.combineBytes(DataUtil.shortToBytes(length),data);
        Client.sendMessage(address,NetConstant.commomPort,data);
    }

    public static void  sendVersion(String address){
        int bestHeight = BlockChainUtil.getBlockHeight();
        byte[] payload =  SerializeUtil.serialize(new Version(BlockConstant.nodeVersion,bestHeight,nodeAddress ));
        logger.info("Send VERSION To "+address);
        sendData(address,BlockConstant.VERSION,payload);

    }
    public static void sendGetBlocks(String address){
        byte[] payload = DataUtil.string2Bytes(nodeAddress);
        logger.info("Send GET_BLOCK To "+address);
        sendData(address,BlockConstant.GET_BLOCK,payload);
    }

    public static void sendInv(String address ,String type,List<byte[]> blockHashes){
        Inventory inventory = new Inventory(nodeAddress,type,blockHashes);
        byte[] payload = SerializeUtil.serialize(inventory);
        logger.info("Send INV To "+address);
        sendData(address,BlockConstant.INV,payload);
    }

    public static void sendGetData(String address ,String type,byte[] blockHash){
        byte[] payload = SerializeUtil.serialize(new GetData(nodeAddress,type,blockHash));
        logger.info("Send GET_DATA To "+address);
        sendData(address,BlockConstant.GET_DATA,payload);
    }
    public static void sendBlock(String address , Block block){
        byte[] payload = SerializeUtil.serialize(new NetBlock(nodeAddress,block));
        logger.info("Send BLOCK To "+address);
        sendData(address,BlockConstant.BLOCK,payload);
    }

    public static void sendAddress(String address){
        byte[] payload = SerializeUtil.serialize(new Address(KnownNodes.getKnownNodes()));
        logger.info("Send ADDRESS To "+address);
        sendData(address,BlockConstant.ADDRESS,payload);
    }

    public static void sendTx(String address, Transaction tx){
        byte[] payload = SerializeUtil.serialize(new Tx(nodeAddress,tx));
        logger.info("Send TX To "+address);
        sendData(address,BlockConstant.TX,payload);
    }

    public static String getLocalIP(){
        try {
            if (isWindowsOS()) {
                return InetAddress.getLocalHost().getHostAddress();
            } else {
                return getLinuxLocalIp();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    /**
     * 判断操作系统是否是Windows
     *
     * @return
     */
    private static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    private static String getLinuxLocalIp() throws SocketException {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress()
                                    .toString();
                            if (!ipaddress.contains("::")
                                    && !ipaddress.contains("0:0:")
                                    && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("获取ip地址异常");
            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        return ip;
    }

    public static String getMiningAddress() {
        return miningAddress;
    }
    public static void setMiningAddress(String miningAddress) {
        ServerUtil.miningAddress = miningAddress;
    }
    public static String getNodeAddress() {
        return nodeAddress;
    }
    public static void setNodeAddress(String nodeAddress) {
        ServerUtil.nodeAddress = nodeAddress;
    }
    public static Map<String, Transaction> getMempool() {
        return mempool;
    }
    public static void setMempool(Map<String, Transaction> mempool) {
        ServerUtil.mempool = mempool;
    }
}
