package com.weiquding.netty.learning.netty.protocol.domain;

/**
 * @author wubai
 * @date 2018/9/23 22:29
 */
public enum MessageType {

    BIZ_REQ,
    BIZ_RSP,
    BIZ_ONEWAY,
    LOGIN_REQ,
    LOGIN_RSP,
    HEARTBEAT_REQ,
    HEARTBEAT_RSP;

    public byte value(){
        return (byte) this.ordinal();
    }

}
