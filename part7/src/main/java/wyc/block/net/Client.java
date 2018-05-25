package wyc.block.net;

import java.io.OutputStream;
import java.net.Socket;;

public class Client {

    public static void sendMessage(String ip,int port,String msg){
            byte[] bstream=msg.getBytes();
            sendMessage( ip, port, bstream);
    }
    public static void sendMessage(String ip,int port,byte[] msg){
        try{
            Socket sck=new Socket(ip,port);
            OutputStream os=sck.getOutputStream();
            os.write(msg);
            sck.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        sendMessage("127.0.0.1",3001,"aaa");
    }
}
