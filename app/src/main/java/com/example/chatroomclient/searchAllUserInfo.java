package com.example.chatroomclient;

import java.util.ArrayList;

public class searchAllUserInfo implements Runnable {
    private String uname;
    private ArrayList<String> userinfo;

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
