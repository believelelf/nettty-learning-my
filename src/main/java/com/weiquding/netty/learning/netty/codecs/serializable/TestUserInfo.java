package com.weiquding.netty.learning.netty.codecs.serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author wubai
 * @date 2018/9/20 21:22
 */
public class TestUserInfo {

    /*
     *The jdk serializable length is:148
     * ---------
     * The byte array serializable length is :24
     */
    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is:" + b.length);
        bos.close();
        System.out.println("---------");
        System.out.println("The byte array serializable length is :" + info.codec().length);
    }


}



