package com.yychat.control;

import java.io.*;
import java.net.Socket;
import com.yychat.model.*;

public class YychatClientConnection {
    public static Socket s;

    public YychatClientConnection() {
        try {
            s = new Socket("127.0.0.1", 3456);
            System.out.println("客户端连接成功：" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean loginValidate(User user) {
        boolean loginSuccess = false;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(user);

            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Message mess = (Message) ois.readObject();

            if (mess.getMessageType().equals(MessageType.LOGIN_VALIDATE_SUCCESS)) {
                loginSuccess = true;
                new ClientReceiverThread(s).start();
            } else {
                s.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loginSuccess;
    }
}