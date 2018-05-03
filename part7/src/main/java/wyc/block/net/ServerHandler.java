package wyc.block.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import wyc.block.util.DataUtil;

public class ServerHandler  extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(ServerHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //接收消息
        ByteBuf byteBuf= (ByteBuf)msg;
        if (byteBuf!=null){
            byte[] bytes=ByteBufUtil.getBytes(byteBuf);
            System.out.println("收到消息："+DataUtil.bytes2String(bytes));
        }
        byteBuf.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    // 建立连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("Channel is Connected,channelId:[" + channel.id() + "]");
        super.channelActive(ctx);
    }

    // 断开连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("Channel is disConnect,channelId:[" + channel.id() + "]");
        // 关闭连接
        ctx.close();
    }
}
