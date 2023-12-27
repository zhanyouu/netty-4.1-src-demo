package org.netty.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class Message {
    private int sequenceId;
    /**
     * 协议版本
     */
    private Byte version = 1;
    /**
     * 序列化方式
     */
    private Byte serializeType;
    /**
     * 指令
     */
    public abstract int getMessageType();
    public static int RPC_REQUEST =1;
    public static int RPC_RESPONSE = 2;
}
