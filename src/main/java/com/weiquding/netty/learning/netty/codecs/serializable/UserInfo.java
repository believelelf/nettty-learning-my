package com.weiquding.netty.learning.netty.codecs.serializable;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author wubai
 * @date 2018/9/20 21:13
 */
public class UserInfo implements Serializable {

    private static final long serivalVersionUID = 1L;

    private String userName;

    private int userID;

    private int age;

    private String name;

    public UserInfo buildUserName(String userName){
        this.userName = userName;
        return this;
    }

    public UserInfo buildUserID(int userID){
        this.userID = userID;
        return this;
    }

    public final String getUserName(){
        return userName;
    }

    public final void setUserName(String userName){
        this.userName = userName;
    }

    public final int getUserID(){
        return userID;
    }

    public final void setUserID(int userID){
        this.userID = userID;
    }

    public byte[] codec(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public byte[] codec(ByteBuffer buffer){
        buffer.clear();
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
