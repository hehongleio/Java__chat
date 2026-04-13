package com.yychat.control;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.yychat.model.User;

public class YychatServer {
    ServerSocket ss;
    Socket s;

    public YychatServer() {
        try {
            // 实验11：启动服务器，监听3456端口
            ss = new ServerSocket(3456);
            System.out.println("服务器启动成功,正在监听3456端口...");
            s = ss.accept(); // 阻塞等待客户端连接
            System.out.println("连接成功:" + s);

            // 实验13：接收客户端发送的User对象，解析登录信息
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            User user = (User) ois.readObject();
            System.out.println("服务器端接收到的客户端登陆信息 userName: " + user.getUserName() + " password: " + user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}