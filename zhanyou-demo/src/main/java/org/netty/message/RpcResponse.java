package org.netty.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RpcResponse extends Message{
    @Override
    public int getMessageType() {
        return RPC_RESPONSE;
    }
}
