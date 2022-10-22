package com.aps.network;
import com.aps.Main;
import com.aps.gui.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
public class Client {
    public static void connect(String ip) {
        Chat chat = new Chat();
        try {
            final Socket clientSocket = new Socket(ip,
                    Main.port);
            final BufferedReader in = new
                    BufferedReader(new
                    InputStreamReader(clientSocket.getInputStream()));
            final PrintWriter out = new
                    PrintWriter(clientSocket.getOutputStream());
            chat.out = out;
            chat.message(-1, "Conectado a " + ip);
            chat.sendMessageOut("nickname=" +
                    Menu.nickname, false);
            //Thread receber msgs
            new Thread(() -> {
                try {
                    String msg = in.readLine();
                    while (msg != null) {
                        if (msg.startsWith("nickname=")) {

                            chat.otherUsername = msg.replace("nickname=", "");
                        } else {
                            chat.message(1, msg);
                        }
                        msg = in.readLine();
                    }
                } catch (SocketException e) {
                    JOptionPane.showMessageDialog(null,
                            "Server desconectado.");
                    try {
                        out.close();
                        clientSocket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    chat.dispose();
                    new Menu();
                } catch (Exception
                        e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (ConnectException | UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "O IP não respondeu.\nVerifique o IP e o firewall.");
            chat.dispose();
            new Menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}