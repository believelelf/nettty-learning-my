package com.weiquding.netty.learning.netty.codecs.marshalling.domain;

import java.io.Serializable;

/**
 * @author wubai
 * @date 2018/9/22 11:20
 */
public class SubscribeReq implements Serializable {

    private static final long serialVersionUID = 1L;

    int subReqID;
    String userName;
    String productName;
    String address;

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
