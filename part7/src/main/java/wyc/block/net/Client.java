package wyc.block.net;

import java.io.OutputStream;
import java.net.Socket;;

public class Client {

    public static void sendMessage(String ip,int port,String msg){
        try{
            Socket sck=new Socket(ip,port);
            byte[] bstream=msg.getBytes("GBK");
            OutputStream os=sck.getOutputStream();
            os.write(bstream);
            sck.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        sendMessage("127.0.0.1",3001,"aaa");
    }
}
