package com.yychat.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FriendList extends JFrame implements ActionListener,MouseListener{

    JPanel friendPanel,strangerPanel;
    JButton myFriendButton1,myStrangerButton1,blackListButton1;
    JButton myFriendButton2,myStrangerButton2,blackListButton2;
    JScrollPane friendListScrollPane,strangerListScrollPane;
    JPanel friendListPanel,strangerListPanel;
    final int MYFRIENDCOUNT=50;
    final int STRANGERCOUNT=20;
    JLabel friendLabel[]=new JLabel[MYFRIENDCOUNT];
    JLabel strangerLabel[]=new JLabel[STRANGERCOUNT];
    CardLayout cl;
    String name;

    public FriendList(String name) {
        this.name=name;
        cl=new CardLayout();
        this.setLayout(cl);

        initFriendPanel();
        initStrangerPanel();

        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        this.setTitle(name+"的好友列表");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(800, 600, 350, 250);
        this.setVisible(true);
    }

    private void initFriendPanel() {
        friendPanel=new JPanel(new BorderLayout());
        myFriendButton1=new JButton("我的好友");
        friendPanel.add(myFriendButton1,BorderLayout.NORTH);

        friendListPanel=new JPanel(new GridLayout(MYFRIENDCOUNT,1));
        for(int i=0;i<friendLabel.length;i++){
            String imageStr="images/"+(int)(Math.random()*6)+".jpg";
            ImageIcon imageIcon=new ImageIcon(imageStr);
            friendLabel[i]=new JLabel(i+"号好友",imageIcon,JLabel.LEFT);
            friendLabel[i].addMouseListener(this);
            friendListPanel.add(friendLabel[i]);
        }

        friendListScrollPane=new JScrollPane(friendListPanel);
        friendPanel.add(friendListScrollPane,BorderLayout.CENTER);

        myStrangerButton1=new JButton("陌生人");
        blackListButton1=new JButton("黑名单");
        JPanel bottomPanel = new JPanel(new GridLayout(2,1));
        bottomPanel.add(myStrangerButton1);
        bottomPanel.add(blackListButton1);
        friendPanel.add(bottomPanel,BorderLayout.SOUTH);

        myStrangerButton1.addActionListener(this);
        blackListButton1.addActionListener(this);

        this.add(friendPanel,"card1");
    }

    private void initStrangerPanel() {
        strangerPanel=new JPanel(new BorderLayout());
        myStrangerButton2=new JButton("陌生人");
        strangerPanel.add(myStrangerButton2,BorderLayout.NORTH);

        strangerListPanel=new JPanel(new GridLayout(STRANGERCOUNT,1));
        for(int i=0;i<strangerLabel.length;i++){
            // 只加这一行：按照PDF添加陌生人头像
            ImageIcon icon = new ImageIcon("images/tortoise.gif");
            strangerLabel[i]=new JLabel(i+"号陌生人",icon,JLabel.LEFT);
            strangerListPanel.add(strangerLabel[i]);
        }

        strangerListScrollPane=new JScrollPane(strangerListPanel);
        strangerPanel.add(strangerListScrollPane,BorderLayout.CENTER);

        myFriendButton2=new JButton("我的好友");
        blackListButton2=new JButton("黑名单");
        JPanel bottomPanel = new JPanel(new GridLayout(2,1));
        bottomPanel.add(myFriendButton2);
        bottomPanel.add(blackListButton2);
        strangerPanel.add(bottomPanel,BorderLayout.SOUTH);

        myFriendButton2.addActionListener(this);
        blackListButton2.addActionListener(this);

        this.add(strangerPanel,"card2");
    }

    public static void main(String args[]){
        FriendList fl=new FriendList("pdh");
    }

    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource()==myFriendButton2){
            cl.show(this.getContentPane(), "card1");
        }
        if(arg0.getSource()==myStrangerButton1){
            cl.show(this.getContentPane(), "card2");
        }
    }

    public void mouseClicked(MouseEvent arg0) {
        if(arg0.getClickCount()==2){
            JLabel jl=(JLabel) arg0.getSource();
            String toName=jl.getText();
            new FriendChat(name+" to "+toName);
        }
    }

    public void mouseEntered(MouseEvent arg0) {
        JLabel jl=(JLabel) arg0.getSource();
        jl.setForeground(Color.RED);
    }

    public void mouseExited(MouseEvent arg0) {
        JLabel jl=(JLabel) arg0.getSource();
        jl.setForeground(Color.BLUE);
    }

    public void mousePressed(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
}