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
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public boolean Login(String res_uname, String res_upassword) {
        this.connect(host, port);
        try {
            String send_info = "Login " + res_uname + " " + res_upassword;
            writer.println(send_info);
            String response = reader.readLine();
            if (response.compareTo("SuccessLogin") == 0) {
//                    change the new page
                this.socket.close();
                return true;
            } else {
                this.socket.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    private void connect(String host, int port) {
        try {
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(host, port));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
