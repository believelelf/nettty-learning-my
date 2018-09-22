package com.weiquding.netty.learning.netty.codecs.marshalling.domain;

import java.io.Serializable;

/**
 * @author wubai
 * @date 2018/9/22 11:20
 */
public class SubscribeRsp implements Serializable {

    private static final long serialVersionUID = 1L;

    int subReqID;
    int respCode;
    String desc;

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
