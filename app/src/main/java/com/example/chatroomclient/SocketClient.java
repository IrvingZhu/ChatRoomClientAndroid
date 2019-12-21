package com.example.chatroomclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;


public class SocketClient {
    private static Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public boolean Login(String res_uname, String res_upassword) {
        this.socket = new Socket();
        host host = new host();
//        this.socket.connect(new InetSocketAddress(host, port));
        try {
            this.socket.connect(new InetSocketAddress(host.host, host.port));
            String send_info = "Login " + res_uname + " " + res_upassword;
            System.out.println("Prepare to send info "+send_info);
//            this.writer = new PrintWriter(this.socket.getOutputStream());
//            writer.println(send_info);
//            writer.flush();
            this.socket.getOutputStream().write(send_info.getBytes("UTF-8"));
            System.out.println("Send Successful");
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String response = reader.readLine();
            System.out.println(response);
            System.out.println(this.socket.isClosed());
            int posi = response.indexOf("/");
            System.out.println(posi);
            String cpy_str = response.substring(0, posi);
            System.out.println(cpy_str);
            if (cpy_str.compareTo("SuccessLogin") == 0) {
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
}
