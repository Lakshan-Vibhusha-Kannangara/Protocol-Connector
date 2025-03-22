package com.vibhusha.backend.protocol.fix.presentation;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class FixMessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        // Decode FIX message from ByteBuf
        String fixMessage = msg.toString(CharsetUtil.UTF_8);
        out.add(fixMessage);
    }
}



