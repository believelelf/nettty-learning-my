package com.weiquding.netty.learning.netty.httpjson.handler;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 封装FullHttpRequest,实现解耦
 * @author wubai
 * @date 2018/9/22 18:48
 */
public class HttpJsonRequest {

    public FullHttpRequest request;
    public Object body;

    public HttpJsonRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
