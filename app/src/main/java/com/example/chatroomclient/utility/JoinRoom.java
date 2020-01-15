package com.example.chatroomclient.utility;

import com.example.chatroomclient.Socket.SocketClient;

public class JoinRoom implements Runnable {
    private boolean result;
    private String send_info;

    public JoinRoom(String send_info){
        this.send_info = send_info;
    }

    @Override
    public void run(){
        SocketClient socket = new SocketClient();
        this.result = socket.joinedRoom(this.send_info);
    }

    public boolean return_res(){
        return this.result;
    }
}
