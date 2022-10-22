package com.aps.gui;
import javax.swing.*;
import java.io.*;
public class Chat extends JFrame {
    private final JTextArea chat;
    public PrintWriter out = null;
    public String otherUsername = "test";
    public void message(int side, String message){
        String msg = "\n";
        if (side == 0) {
            msg+="> "+Menu.nickname+": ";
        }else if(side == -1){
            msg+="[!] ";
        }else{
            msg+=otherUsername+": ";
        }
        msg+=message;
        chat.setText(chat.getText() + msg);
    }
    public void sendMessageOut(String msg, boolean
            visible){
        if(out!=null){
            out.println(msg);
            out.flush();
            if(visible) message(0, msg);
        }
    }
    public Chat(){
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        setTitle("APS - Messenger");
        setSize(400, 400);
        JPanel contentPane = new JPanel();
        contentPane.setSize(400, 250);
        contentPane.setLayout(null);
        JTextField msg = new JTextField();
        msg.setBounds(10,315,370, 20);
        msg.addActionListener(e -> {
            sendMessageOut(msg.getText(), true);
            msg.setText("");
        });
        contentPane.add(msg);
        chat = new JTextArea();
        chat.setBounds(10,10,370, 300);
        chat.setEditable(false);
        contentPane.add(chat);
        msg.grabFocus();
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

