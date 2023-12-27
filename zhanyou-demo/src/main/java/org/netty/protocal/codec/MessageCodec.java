package org.netty.protocal.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.CharsetUtil;
import org.netty.message.Message;
import org.netty.message.RpcRequest;
import org.netty.message.RpcResponse;
import org.netty.serializer.Serializer;
import org.netty.serializer.impl.JSONSerializer;
import org.netty.serializer.impl.KryoSerializer;
import org.netty.serializer.impl.ProtoStuffSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编解码器
 */
public class MessageCodec extends ByteToMessageCodec<Message> {
    private static final Map<Byte, Serializer> serializerMap;
    private static final Map<Integer, Class<? extends Message>> messageTypeMap;

    static {
        messageTypeMap = new HashMap<>();
        messageTypeMap.put(Message.RPC_REQUEST, RpcRequest.class);
        messageTypeMap.put(Message.RPC_RESPONSE, RpcResponse.class);
        serializerMap = new HashMap<>();
        Serializer jsonSerializer = new JSONSerializer();
        serializerMap.put(jsonSerializer.getSerializerAlgorithm(), jsonSerializer);
        Serializer protoStuffSerializer = new ProtoStuffSerializer();
        serializerMap.put(protoStuffSerializer.getSerializerAlgorithm(), protoStuffSerializer);
        Serializer kryoSerializer = new KryoSerializer();
        serializerMap.put(kryoSerializer.getSerializerAlgorithm(), kryoSerializer);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        //魔数
        out.writeBytes(new byte[]{1, 2, 3, 4});
        //版本号
        out.writeByte(msg.getVersion());
        //序列化类型
        out.writeByte(msg.getSerializeType());
        //指令
        out.writeByte(msg.getMessageType());
        //序号
        out.writeInt(msg.getSequenceId());
        //对齐填充
        out.writeByte(0xff);
        Serializer serializer = serializerMap.get(msg.getSerializeType());
        byte[] bytes = serializer.serialize(msg);
        //内容长度
        out.writeInt(bytes.length);
        //消息内容
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializeType = in.readByte();
        int messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        Message message = serializerMap.get(serializeType).deserialize(messageTypeMap.get(messageType), bytes);
        out.add(message);
        System.out.println(message);
    }
}
