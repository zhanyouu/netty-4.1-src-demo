package org.netty.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class Message {
    /**
     * 魔数
     */
    private byte[] magicNum = new byte[]{1,2,3,4};
    /**
     * 序号
     */
    private int sequenceId = 1;
    /**
     * 协议版本
     */
    private Byte version = 1;
    /**
     * 序列化方式
     */
    private Byte serializeType = 1;
    /**
     * 指令
     */
    public abstract int getMessageType();
    public static int RPC_REQUEST =1;
    public static int RPC_RESPONSE = 2;
}
