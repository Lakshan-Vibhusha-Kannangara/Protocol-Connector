package com.vibhusha.backend.protocol.fix.osi;

import com.vibhusha.backend.protocol.fix.application.FixClientHandler;
import com.vibhusha.backend.protocol.fix.session.FixSessionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class FixClient {
    private final String host;
    private final int port;

    public FixClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8)); // presentation layer
                            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));  // presentation layer
                            ch.pipeline().addLast(new FixSessionHandler()); // Add session handler
                            ch.pipeline().addLast(new FixClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();
            System.out.println("FIX Client connected to " + host + ":" + port);

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}