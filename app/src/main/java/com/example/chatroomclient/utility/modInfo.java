package com.example.chatroomclient.utility;

import com.example.chatroomclient.Socket.SocketClient;

public class modInfo implements Runnable {
    private int type;
    private String uid;
    private String info;
    private boolean result;

    public modInfo(String uid, String info, int type){
//        type == 0 uname
//        type == 1 upsw
        this.type = type;
        this.uid = uid;
        this.info = info;
    }

    @Override
    public void run(){
        SocketClient socket = new SocketClient();
        this.result = socket.modifyInfo(this.uid, this.info, this.type);
    }

    public boolean return_result(){
        return this.result;
    }
}
