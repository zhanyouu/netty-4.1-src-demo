package org.netty.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.CharsetUtil;
import org.netty.message.Message;

import java.util.List;

/**
 * 编解码器
 */
public class MessageCodec extends ByteToMessageCodec<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        // 将对象转换为 JSON 字符串
        String json = JSON.toJSONString(msg);
        // 将 JSON 字符串转换为字节数组并写入 ByteBuf 中
        out.writeBytes(json.getBytes(CharsetUtil.UTF_8));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // 从 ByteBuf 中读取字节数组
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        // 将字节数组转换为 JSON 字符串
        String json = new String(bytes, CharsetUtil.UTF_8);
        Message message = JSONObject.parseObject(json, Message.class);
        if (message != null) {
            out.add(message);
        }
    }
}
