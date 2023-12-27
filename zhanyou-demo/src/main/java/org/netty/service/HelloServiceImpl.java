package org.netty.service;

public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String msg) {
        return "你好"+msg;
    }
}
