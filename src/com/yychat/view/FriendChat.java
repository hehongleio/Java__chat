package com.yychat.view;

import java.awt.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import javax.swing.*;
import com.yychat.control.YychatClientConnection;
import com.yychat.model.*;

public class FriendChat extends JFrame implements ActionListener {
    private JTextArea jta;
    private JTextField jtf;
    private JButton jb;
    private String sender;
    private String receiver;

    public FriendChat(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;

        jta = new JTextArea();
        jta.setForeground(Color.RED);
        JScrollPane jsp = new JScrollPane(jta);
        this.add(jsp, "Center");

        jtf = new JTextField(25);
        jb = new JButton("发送");
        jb.addActionListener(this);
        jb.setForeground(Color.BLUE);
        JPanel jp = new JPanel();
        jp.add(jtf);
        jp.add(jb);
        this.add(jp, "South");

        this.setSize(350, 250);
        this.setLocationRelativeTo(null);
        this.setTitle(sender + " to " + receiver + "的聊天界面");
        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb) {
            String content = jtf.getText();
            if (content.trim().isEmpty()) return;
            jta.append("我: " + content + "\r\n");

            Message mess = new Message();
            mess.setSender(sender);
            mess.setReceiver(receiver);
            mess.setContent(content);
            mess.setMessageType(MessageType.COMMON_CHAT_MESSAGE);

            try {
                ObjectOutputStream oos = new ObjectOutputStream(YychatClientConnection.s.getOutputStream());
                oos.writeObject(mess);
                jtf.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void append(Message mess) {
        jta.append(mess.getSender() + ": " + mess.getContent() + "\r\n");
    }
}