package com.yychat.model;
import java.io.Serializable;

// 必须实现Serializable接口，支持网络对象传输
public class User implements Serializable {
    String userName;
    String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}