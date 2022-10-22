package com.aps.network;
import com.aps.Main;
import com.aps.gui.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
    public class Server {
        public static String getIpAddress() {
            try {
                for (Enumeration<NetworkInterface> en =
                     NetworkInterface.getNetworkInterfaces();
                     en.hasMoreElements();) {
                    NetworkInterface iface =
                            en.nextElement();
                    for (Enumeration<InetAddress> eIps =
                         iface.getInetAddresses(); eIps.hasMoreElements();) {
                        InetAddress inetAddress =
                                eIps.nextElement();
                        if (inetAddress instanceof
                                Inet4Address && !inetAddress.isLoopbackAddress()) return
                                inetAddress.getHostAddress();
                    }
                }
            } catch (SocketException ignored) {}
            return null;
        }
        public static void startServer() {
            try {
                Chat chat = new Chat();
                final ServerSocket serverSocket = new
                        ServerSocket(Main.port);
                chat.message(-1, "Server iniciado em "+getIpAddress());
                        Socket clientSocket = serverSocket.accept();
                chat.message(-1, "Client conectado: "+clientSocket.getInetAddress().toString());
                final BufferedReader in = new
                        BufferedReader(new
                        InputStreamReader(clientSocket.getInputStream()));
                final PrintWriter out = new
                        PrintWriter(clientSocket.getOutputStream());
                chat.out = out;
                chat.sendMessageOut("nickname="+
                        Menu.nickname, false);
                //Thread receber msgs
                new Thread(() -> {
                    try {
                        String msg = in.readLine();
                        while(msg!=null){
                            if(msg.startsWith("nickname=")){

                                chat.otherUsername=msg.replace("nickname=", "");
                            }else{
                                chat.message(1, msg);
                            }
                            msg = in.readLine();
                        }
                    }catch (SocketException e){
                        JOptionPane.showMessageDialog(null,
                                "Client desconectado.");
                        try {
                            chat.out.close();
                            chat.out = null;
                            out.close();
                            in.close();
                            serverSocket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        chat.dispose();
                        new Menu();
                    }catch (Exception
                            e){e.printStackTrace();}
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

