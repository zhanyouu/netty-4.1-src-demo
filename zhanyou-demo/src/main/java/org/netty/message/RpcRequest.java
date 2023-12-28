package org.netty.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RpcRequest extends Message{

    private String interfaceName;
    private String methodName;
    private Class<?> returnType;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
    @Override
    public int getMessageType() {
        return RPC_REQUEST;
    }
}
