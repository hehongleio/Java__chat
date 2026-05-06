package com.yychat.control;

import java.io.*;
import java.net.*;
import java.util.*;
import com.yychat.model.*;

public class ServerReceiverThread extends Thread {
    private Socket s;

    public ServerReceiverThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message mess = (Message) ois.readObject();

                if (mess.getMessageType().equals(MessageType.COMMON_CHAT_MESSAGE)) {
                    System.out.println(mess.getSender() + "对" + mess.getReceiver() + "说:" + mess.getContent());
                    Socket receiverSocket = YychatServer.hmSocket.get(mess.getReceiver());
                    System.out.println("接收方" + mess.getReceiver() + "的Socket对象:" + receiverSocket);
                    if (receiverSocket != null) {
                        ObjectOutputStream oos = new ObjectOutputStream(receiverSocket.getOutputStream());
                        oos.writeObject(mess);
                    } else {
                        System.out.println(mess.getReceiver() + "不在线上");
                    }
                }
                else if (mess.getMessageType().equals(MessageType.REQUEST_ONLINE_FRIEND)) {
                    Set<String> onlineSet = YychatServer.hmSocket.keySet();
                    Iterator<String> it = onlineSet.iterator();
                    String onlineFriend = "";
                    while (it.hasNext()) {
                        onlineFriend = "+" + it.next() + onlineFriend;
                    }
                    mess.setReceiver(mess.getSender());
                    mess.setSender("Server");
                    mess.setMessageType(MessageType.RESPONSE_ONLINE_FRIEND);
                    mess.setContent(onlineFriend);
                    sendMessage(s, mess);
                }
                else if (mess.getMessageType().equals(MessageType.NEW_ONLINE_TO_ALL_FRIEND)) {
                    Set<String> onlineSet = YychatServer.hmSocket.keySet();
                    Iterator<String> it = onlineSet.iterator();
                    while (it.hasNext()) {
                        String receiver = it.next();
                        Socket receiverSocket = YychatServer.hmSocket.get(receiver);
                        if (receiverSocket != null && !receiver.equals(mess.getSender())) {
                            Message newMess = new Message();
                            newMess.setMessageType(MessageType.NEW_ONLINE_FRIEND);
                            newMess.setSender("Server");
                            newMess.setReceiver(receiver);
                            newMess.setContent(mess.getSender());
                            sendMessage(receiverSocket, newMess);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(Socket s, Message mess) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(mess);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}