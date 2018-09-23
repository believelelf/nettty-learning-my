package com.weiquding.netty.learning.netty.protocol.domain;

/**
 * @author wubai
 * @date 2018/9/23 19:34
 */
public class NettyMessage {

    private  Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
