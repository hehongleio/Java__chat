package com.yychat.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FriendChat extends JFrame implements ActionListener{

    JTextArea jta;
    JScrollPane jsp;
    JTextField jtf;
    JButton jb;

    public FriendChat(String oneToAnother) {
        jta=new JTextArea();
        jta.setForeground(Color.RED);
        jsp=new JScrollPane(jta);
        this.add(jsp,BorderLayout.CENTER);

        jtf=new JTextField(25);
        jb=new JButton("发送");
        jb.addActionListener(this);
        jb.setForeground(Color.BLUE);

        JPanel jp=new JPanel();
        jp.add(jtf);
        jp.add(jb);
        this.add(jp,BorderLayout.SOUTH);

        this.setSize(350,250);
        this.setLocationRelativeTo(null);
        this.setTitle(oneToAnother+"的聊天界面");
        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb){
            jta.append(jtf.getText()+"\r\n");
        }
    }
}