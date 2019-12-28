package com.example.chatroomclient;

//import Runnable;

import java.util.ArrayList;

public class searchAllRoom implements Runnable {

    public String uid;
    public ArrayList<String> roomset;

    public searchAllRoom(String uid){
        this.uid = uid;
    }

    @Override
    public void run(){
        SocketClient socket = new SocketClient();
        this.roomset = socket.searchAllRoom(this.uid);
    }

    public ArrayList<String> return_roomset(){
        return this.roomset;
    }
}
