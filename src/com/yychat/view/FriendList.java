package com.yychat.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import com.yychat.model.Message;

public class FriendList extends JFrame implements ActionListener, MouseListener {
    public static HashMap<String, FriendChat> hmFriendChat = new HashMap<>();

    private JPanel friendPanel, strangerPanel;
    private JButton myFriendButton1, myStrangerButton1, blackListButton1;
    private JButton myFriendButton2, myStrangerButton2, blackListButton2;
    private JScrollPane friendListScrollPane, strangerListScrollPane;
    private JPanel friendListPanel, strangerListPanel;
    private final int MYFRIENDCOUNT = 50;
    private JLabel[] friendLabel = new JLabel[MYFRIENDCOUNT];
    private final int STRANGERCOUNT = 20;
    private JLabel[] strangerLabel = new JLabel[STRANGERCOUNT];
    private CardLayout cl;
    private String name;

    public FriendList(String name) {
        this.name = name;

        friendPanel = new JPanel(new BorderLayout());
        myFriendButton1 = new JButton("我的好友");
        friendPanel.add(myFriendButton1, "North");

        friendListPanel = new JPanel(new GridLayout(MYFRIENDCOUNT, 1));
        for (int i = 0; i < MYFRIENDCOUNT; i++) {
            String imageStr = "images/" + (int)(Math.random() * 6) + ".jpg";
            ImageIcon icon = new ImageIcon(imageStr);
            friendLabel[i] = new JLabel(i + "", icon, JLabel.LEFT);
            friendLabel[i].addMouseListener(this);
            friendLabel[i].setEnabled(false);
            friendListPanel.add(friendLabel[i]);
        }
        friendListScrollPane = new JScrollPane(friendListPanel);
        friendPanel.add(friendListScrollPane, "Center");

        myStrangerButton1 = new JButton("陌生人");
        myStrangerButton1.addActionListener(this);
        blackListButton1 = new JButton("黑名单");
        JPanel strangerBlackPanel = new JPanel(new GridLayout(2, 1));
        strangerBlackPanel.add(myStrangerButton1);
        strangerBlackPanel.add(blackListButton1);
        friendPanel.add(strangerBlackPanel, "South");

        strangerPanel = new JPanel(new BorderLayout());
        myFriendButton2 = new JButton("我的好友");
        myFriendButton2.addActionListener(this);
        myStrangerButton2 = new JButton("陌生人");
        JPanel friendStrangerPanel = new JPanel(new GridLayout(2, 1));
        friendStrangerPanel.add(myFriendButton2);
        friendStrangerPanel.add(myStrangerButton2);
        strangerPanel.add(friendStrangerPanel, "North");

        strangerListPanel = new JPanel(new GridLayout(STRANGERCOUNT, 1));
        for (int i = 0; i < STRANGERCOUNT; i++) {
            strangerLabel[i] = new JLabel(i + "号陌生人", new ImageIcon("images/tortoise.gif"), JLabel.LEFT);
            strangerListPanel.add(strangerLabel[i]);
        }
        strangerListScrollPane = new JScrollPane(strangerListPanel);
        strangerPanel.add(strangerListScrollPane, "Center");

        blackListButton2 = new JButton("黑名单");
        strangerPanel.add(blackListButton2, "South");

        cl = new CardLayout();
        this.setLayout(cl);
        this.add(friendPanel, "card1");
        this.add(strangerPanel, "card2");

        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        this.setTitle(name + "的好友列表");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(800, 600, 350, 350);
        this.setVisible(true);
    }

    public void activateOnlineFriendIcon(Message mess) {
        String onlineFriend = mess.getContent();
        String[] onlineFriendName = onlineFriend.split("\\+");
        for (int i = 1; i < onlineFriendName.length; i++) {
            try {
                int idx = Integer.parseInt(onlineFriendName[i]);
                if (idx >= 0 && idx < MYFRIENDCOUNT) {
                    friendLabel[idx].setEnabled(true);
                }
            } catch (NumberFormatException e) {
                System.out.println("无效的用户名格式");
            }
        }
    }

    public void activateNewOnlineFriend(String friendName) {
        try {
            int idx = Integer.parseInt(friendName);
            if (idx >= 0 && idx < MYFRIENDCOUNT) {
                friendLabel[idx].setEnabled(true);
            }
        } catch (NumberFormatException e) {
            System.out.println("不是数字用户名，无法激活");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myFriendButton2) {
            cl.show(this.getContentPane(), "card1");
        } else if (e.getSource() == myStrangerButton1) {
            cl.show(this.getContentPane(), "card2");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JLabel jl = (JLabel) e.getSource();
            String toName = jl.getText();
            FriendChat fc = new FriendChat(name, toName);
            hmFriendChat.put(name + "to" + toName, fc);
        }
    }

    @Override public void mouseEntered(MouseEvent e) {
        JLabel jl = (JLabel) e.getSource();
        jl.setForeground(Color.RED);
    }

    @Override public void mouseExited(MouseEvent e) {
        JLabel jl = (JLabel) e.getSource();
        jl.setForeground(Color.BLUE);
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
}