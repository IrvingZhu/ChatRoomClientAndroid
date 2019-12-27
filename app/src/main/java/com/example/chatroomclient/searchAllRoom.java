package com.example.chatroomclient;

//import Runnable;

public class searchAllRoom implements Runnable {

    public string uid;

    public searchAllRoom(string uid){
        this.uid = uid;
    }

    @Override
    public void run(){
        SocketClient socket = new SocketClient();
    }
}
