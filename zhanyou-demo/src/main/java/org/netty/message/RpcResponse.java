package org.netty.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RpcResponse extends Message{
    private Boolean success;
    private Object value;
    @Override
    public int getMessageType() {
        return RPC_RESPONSE;
    }
}
