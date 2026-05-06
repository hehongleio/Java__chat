package com.yychat.view;

import java.awt.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import javax.swing.*;
import com.yychat.control.YychatClientConnection;
import com.yychat.model.*;

public class ClientLogin extends JFrame implements ActionListener {
    public static java.util.HashMap<String, FriendList> hmFriendList = new java.util.HashMap<>();

    private JLabel jl;
    private JButton jb1, jb2, jb3;
    private JPanel jp;
    private JLabel jl1, jl2, jl3, jl4;
    private JTextField jtf;
    private JPasswordField jpf;
    private JButton jb4;
    private JCheckBox jc1, jc2;
    private JPanel jp1, jp2, jp3;
    private JTabbedPane jtp;

    public ClientLogin() {
        jl = new JLabel(new ImageIcon("images/head.gif"));
        this.add(jl, "North");

        jl1 = new JLabel("YY号码: ", JLabel.CENTER);
        jl2 = new JLabel("YY密码: ", JLabel.CENTER);
        jl3 = new JLabel("忘记密码", JLabel.CENTER);
        jl3.setForeground(Color.blue);
        jl4 = new JLabel("申请密码保护", JLabel.CENTER);
        jb4 = new JButton(new ImageIcon("images/clear.gif"));
        jtf = new JTextField();
        jpf = new JPasswordField();
        jc1 = new JCheckBox("隐身登陆");
        jc2 = new JCheckBox("记住密码");

        jp1 = new JPanel(new GridLayout(3, 3));
        jp1.add(jl1); jp1.add(jtf); jp1.add(jb4);
        jp1.add(jl2); jp1.add(jpf); jp1.add(jl3);
        jp1.add(jc1); jp1.add(jc2); jp1.add(jl4);

        jtp = new JTabbedPane();
        jtp.add(jp1, "YY号码");
        jp2 = new JPanel();
        jp3 = new JPanel();
        jtp.add(jp2, "手机号码");
        jtp.add(jp3, "电子邮箱");
        this.add(jtp, "Center");

        jb1 = new JButton(new ImageIcon("images/login.gif"));
        jb1.addActionListener(this);
        jb2 = new JButton(new ImageIcon("images/register.gif"));
        jb3 = new JButton(new ImageIcon("images/cancel.gif"));
        jp = new JPanel();
        jp.add(jb1); jp.add(jb2); jp.add(jb3);
        this.add(jp, "South");

        Image im = new ImageIcon("images/duck2.gif").getImage();
        this.setIconImage(im);
        this.setLocationRelativeTo(null);
        this.setSize(350, 250);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("YY聊天");
        this.setVisible(true);
    }

    private void sendMessage(Message mess) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(YychatClientConnection.s.getOutputStream());
            oos.writeObject(mess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
            String name = jtf.getText();
            String password = new String(jpf.getPassword());
            User user = new User();
            user.setUserName(name);
            user.setPassword(password);

            if (new YychatClientConnection().loginValidate(user)) {
                hmFriendList.put(name, new FriendList(name));
                System.out.println("客户端登陆成功！");

                Message reqOnline = new Message();
                reqOnline.setSender(name);
                reqOnline.setReceiver("Server");
                reqOnline.setMessageType(MessageType.REQUEST_ONLINE_FRIEND);
                sendMessage(reqOnline);

                Message newUser = new Message();
                newUser.setSender(name);
                newUser.setReceiver("Server");
                newUser.setMessageType(MessageType.NEW_ONLINE_TO_ALL_FRIEND);
                sendMessage(newUser);

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "密码错误，请重新登陆！");
            }
        }
    }

    public static void main(String[] args) {
        new ClientLogin();
    }
}