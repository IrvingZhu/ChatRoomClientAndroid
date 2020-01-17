package com.example.chatroomclient.utility;

import com.example.chatroomclient.Socket.SocketClient;

public class createRoom_Search implements Runnable {
    private boolean result;
    private String send_info;

    public createRoom_Search(String send_info){
        this.send_info = send_info;
    }

    @Override
    public void run(){
        SocketClient socket = new SocketClient();
        this.result = socket.createRoom(this.send_info);
    }

    public boolean return_res(){
        return this.result;
    }
}
