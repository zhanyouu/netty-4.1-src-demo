package org.netty.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RpcRequest extends Message{
    private String content;
    @Override
    public int getMessageType() {
        return RPC_REQUEST;
    }
}
