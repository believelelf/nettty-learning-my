package com.weiquding.netty.learning.netty.httpjson.handler;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author wubai
 * @date 2018/9/22 20:01
 */
public class HttpJsonResponse {

    private FullHttpResponse httpResponse;
    private Object result;

    public HttpJsonResponse(FullHttpResponse httpResponse, Object result) {
        this.httpResponse = httpResponse;
        this.result = result;
    }

    public FullHttpResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(FullHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
