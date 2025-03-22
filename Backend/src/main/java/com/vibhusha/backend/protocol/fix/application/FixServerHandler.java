package com.vibhusha.backend.protocol.fix.application;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class FixServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("Received FIX message: " + msg);
        // Process FIX message (e.g., parse and handle orders)
        ctx.writeAndFlush("ACK: " + msg); // Send acknowledgment
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}