package com.vibhusha.backend.protocol.fix.presentation;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class FixMessageEncoder extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) {
        // Encode FIX message to ByteBuf
        ByteBuf encoded = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
        out.add(encoded);
    }

}
