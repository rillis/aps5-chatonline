package com.aps.gui;
import com.aps.network.Client;
import com.aps.network.Server;
import javax.swing.*;
public class Menu extends JFrame {
    public static String nickname = "";
    public Menu(){
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        setTitle("APS - Messenger");
        setSize(400, 250);
        JPanel contentPane = new JPanel();
        contentPane.setSize(400, 250);
        contentPane.setLayout(null);
        JLabel lblNickname = new JLabel("Nome de usuário:");
        lblNickname.setBounds(10,10,370, 20);
        contentPane.add(lblNickname);
        JTextField txtNickname = new
                JTextField(nickname);
        txtNickname.setBounds(10,30,370, 20);
        contentPane.add(txtNickname);
        JSeparator separator = new JSeparator();
        separator.setBounds(10, 55, 370, 3);
        contentPane.add(separator);
        JLabel lblServer = new JLabel("Server:");
        lblServer.setBounds(10,60,370, 20);
        contentPane.add(lblServer);
        JTextField txtServer = new
                JTextField(Server.getIpAddress());
        txtServer.setBounds(10,80,370, 20);
        txtServer.setEnabled(false);
        contentPane.add(txtServer);
        JButton btnServer = new JButton("Iniciar Servidor");
                btnServer.setBounds(10,105, 370, 20);
        btnServer.addActionListener(e -> {
            if(txtNickname.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Nome não preenchido!");
            }else{
                nickname = txtNickname.getText();
                this.dispose();
                new Thread(() ->
                        Server.startServer()).start();
            }
        });
        contentPane.add(btnServer);
        separator = new JSeparator();
        separator.setBounds(10, 130, 370, 3);
        contentPane.add(separator);
        JLabel lblClient = new JLabel("Client:");
        lblClient.setBounds(10,135,370, 20);
        contentPane.add(lblClient);
        JTextField txtClient = new
                JTextField("192.168.0.1");
        txtClient.setBounds(10,155,370, 20);
        contentPane.add(txtClient);
        JButton btnClient = new JButton("Conectar");
        btnClient.setBounds(10,180, 370, 20);
        btnClient.addActionListener(e -> {
            if(txtNickname.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Nome não preenchido!");
            }else{
                nickname = txtNickname.getText();
                this.dispose();
                new Thread(() ->
                        Client.connect(txtClient.getText())).start();
            }
        });
        contentPane.add(btnClient);
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}