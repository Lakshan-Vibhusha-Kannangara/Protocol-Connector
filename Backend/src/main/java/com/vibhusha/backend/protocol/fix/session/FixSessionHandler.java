package com.vibhusha.backend.protocol.fix.session;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FixSessionHandler extends SimpleChannelInboundHandler<String> {

    private static final int HEARTBEAT_INTERVAL = 30; // Heartbeat interval in seconds
    private ScheduledFuture<?> heartbeatFuture;
    private int incomingSeqNum = 1;
    private int outgoingSeqNum = 1;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send logon message when the connection is established
        sendLogon(ctx);
        // Schedule heartbeats
        scheduleHeartbeat(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Handle incoming FIX messages
        if (isLogonMessage(msg)) {
            handleLogon(ctx);
        } else if (isLogoutMessage(msg)) {
            handleLogout(ctx);
        } else if (isHeartbeat(msg)) {
            handleHeartbeat(ctx);
        } else {
            // Handle other FIX messages
            System.out.println("Received FIX message: " + msg);
            incomingSeqNum++;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // Cancel heartbeat when the channel is inactive
        if (heartbeatFuture != null) {
            heartbeatFuture.cancel(false);
        }
        System.out.println("Session disconnected.");
    }

    private void sendLogon(ChannelHandlerContext ctx) {
        String logonMsg = "8=FIX.4.4|35=A|49=CLIENT|56=SERVER|34=" + outgoingSeqNum + "|52=" + System.currentTimeMillis() + "|98=0|108=" + HEARTBEAT_INTERVAL + "|";
        ctx.writeAndFlush(logonMsg);
        outgoingSeqNum++;
    }

    private void handleLogon(ChannelHandlerContext ctx) {
        System.out.println("Logon received. Session established.");
    }

    private void handleLogout(ChannelHandlerContext ctx) {
        System.out.println("Logout received. Closing session.");
        ctx.close();
    }

    private void handleHeartbeat(ChannelHandlerContext ctx) {
        System.out.println("Heartbeat received.");
    }

    private void scheduleHeartbeat(ChannelHandlerContext ctx) {
        heartbeatFuture = ctx.executor().scheduleAtFixedRate(() -> {
            String heartbeatMsg = "8=FIX.4.4|35=0|49=CLIENT|56=SERVER|34=" + outgoingSeqNum + "|52=" + System.currentTimeMillis() + "|";
            ctx.writeAndFlush(heartbeatMsg);
            outgoingSeqNum++;
        }, HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }

    private boolean isLogonMessage(String msg) {
        return msg.contains("35=A");
    }

    private boolean isLogoutMessage(String msg) {
        return msg.contains("35=5");
    }

    private boolean isHeartbeat(String msg) {
        return msg.contains("35=0");
    }
}
