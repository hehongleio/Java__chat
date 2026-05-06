package com.yychat.control;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.HashMap;
import com.yychat.model.*;

public class YychatServer {
    public static HashMap<String, Socket> hmSocket = new HashMap<>();

    public YychatServer() {
        try (ServerSocket ss = new ServerSocket(3456)) {
            System.out.println("服务器启动成功，正在监听3456端口...");
            while (true) {
                Socket s = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User user = (User) ois.readObject();
                String userName = user.getUserName();
                String password = user.getPassword();
                System.out.println(userName + "连接成功：" + s);
                System.out.println("服务器端接收到的客户端登陆信息 userName:" + userName + " password:" + password);

                // 数据库验证
                boolean loginSuccess = false;
                String db_url = "jdbc:mysql://127.0.0.1:3306/yychat2022s?useUnicode=true&characterEncoding=utf-8";
                String db_user = "root";
                String db_pwd = "Root2217058747";  // 请根据实际密码修改
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
                    String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, userName);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();
                    loginSuccess = rs.next();
                    rs.close();
                    pstmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                Message mess = new Message();
                if (loginSuccess) {
                    System.out.println("密码验证通过！");
                    mess.setMessageType(MessageType.LOGIN_VALIDATE_SUCCESS);
                    oos.writeObject(mess);
                    hmSocket.put(userName, s);
                    new ServerReceiverThread(s).start();
                    System.out.println("启动线程成功！");
                } else {
                    System.out.println("密码验证失败！");
                    mess.setMessageType(MessageType.LOGIN_VALIDATE_FAILURE);
                    oos.writeObject(mess);
                    s.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}