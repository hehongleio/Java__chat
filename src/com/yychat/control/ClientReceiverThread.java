package com.yychat.control;

import java.io.*;
import java.net.Socket;
import com.yychat.model.*;
import com.yychat.view.*;

public class ClientReceiverThread extends Thread {
    private Socket s;

    public ClientReceiverThread(Socket s) {
        this.s = s;
    }

    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message mess = (Message) ois.readObject();

                if (mess.getMessageType().equals(MessageType.COMMON_CHAT_MESSAGE)) {
                    String receiver = mess.getReceiver();
                    String sender = mess.getSender();
                    FriendChat fc = FriendList.hmFriendChat.get(receiver + "to" + sender);
                    if (fc != null) {
                        fc.append(mess);
                    } else {
                        System.out.println("请打开" + receiver + "to" + sender + "的聊天界面");
                    }
                }
                else if (mess.getMessageType().equals(MessageType.RESPONSE_ONLINE_FRIEND)) {
                    FriendList fl = ClientLogin.hmFriendList.get(mess.getReceiver());
                    if (fl != null) {
                        fl.activateOnlineFriendIcon(mess);
                    }
                }
                else if (mess.getMessageType().equals(MessageType.NEW_ONLINE_FRIEND)) {
                    FriendList fl = ClientLogin.hmFriendList.get(mess.getReceiver());
                    if (fl != null) {
                        fl.activateNewOnlineFriend(mess.getContent());
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}