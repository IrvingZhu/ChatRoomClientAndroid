package com.example.chatroomclient.utility;

import com.example.chatroomclient.Socket.SocketClient;

import java.util.ArrayList;

public class searchAllUserInfo implements Runnable {
    private String uname;
    private ArrayList<String> userinfo;

    public searchAllUserInfo(String uname){
        this.uname = uname;
    }

    @Override
    public void run(){
        SocketClient socket = new SocketClient();
        ArrayList<String> res = socket.searchAllUserInfo(this.uname);
        this.userinfo = res;
    }

    public ArrayList<String> return_userinfo(){
        return this.userinfo;
    }
}
