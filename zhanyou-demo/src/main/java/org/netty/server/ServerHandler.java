package org.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.netty.message.RpcRequest;
import org.netty.message.RpcResponse;
import org.netty.service.HelloServiceImpl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Map<String,Class<?>> interfaceMap = new HashMap<>();
    static {
        interfaceMap.put("org.netty.service.HelloService", HelloServiceImpl.class);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("CLIENT: " + getRemoteAddress(ctx) + " 接入连接");
    }

    private static String getRemoteAddress(ChannelHandlerContext ctx) {
        return Optional.ofNullable(ctx.channel().remoteAddress().toString()).orElse("").replace("/", "");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            System.out.println("服务端接收:" + msg);
            RpcRequest request = (RpcRequest) msg;
            Class<?> clazz = interfaceMap.get(request.getInterfaceName());
            Method method = clazz.getMethod(request.getMethodName(), request.getParameterTypes());
            Object result = method.invoke(clazz.newInstance(), request.getParameters());
            RpcResponse response = new RpcResponse();
            response.setSuccess(true);
            response.setValue(result);
            ctx.writeAndFlush(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
