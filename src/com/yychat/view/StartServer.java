package com.yychat.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.yychat.control.YychatServer;

public class StartServer extends JFrame implements ActionListener {
    private JButton jb1, jb2;

    public StartServer() {
        jb1 = new JButton("启动服务器");
        jb1.setFont(new Font("宋体", Font.BOLD, 25));
        jb1.addActionListener(this);
        jb2 = new JButton("停止服务器");
        jb2.setFont(new Font("宋体", Font.BOLD, 25));

        this.setLayout(new GridLayout(1, 2));
        this.add(jb1);
        this.add(jb2);
        this.setSize(400, 100);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        this.setTitle("YYchat服务器");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
            new YychatServer();
        }
    }

    public static void main(String[] args) {
        new StartServer();
    }
}