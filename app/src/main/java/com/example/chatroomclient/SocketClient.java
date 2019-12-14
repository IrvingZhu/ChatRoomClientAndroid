package com.example.chatroomclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient {
    private static final String host = "127.0.0.1";
    private static final int port = 8888;

    public void connect(String host, int port) {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
//                writer.println("Hello Server, I am " + socket.getInetAddress());
//                String response = reader.readLine();
//                System.out.println("server return message : " + response);
//
//                Thread.sleep(3000);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
