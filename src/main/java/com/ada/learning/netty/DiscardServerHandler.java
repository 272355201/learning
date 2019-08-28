package com.ada.learning.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.FastThreadLocal;


public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    private int i;
    FastThreadLocal<Integer> fastThreadLocal = new FastThreadLocal<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        i++;
        System.out.println(Thread.currentThread().getClass() + Thread.currentThread().getName() + "integer--" + i + " " + fastThreadLocal.get());
        fastThreadLocal.set(i);
        fastThreadLocal.getIfExists();
        if (i % 3 != 0){
            return;
        }
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) { // (1)
                char c = (char) in.readByte();
                if (c == 'q'){
                    ctx.close();
                }
                System.out.print(c);
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}