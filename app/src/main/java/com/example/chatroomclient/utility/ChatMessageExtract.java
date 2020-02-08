package com.example.chatroomclient.utility;

import java.util.ArrayList;

public class ChatMessageExtract {

    public ArrayList<String> Extract(String src){
        ArrayList<String> res = new ArrayList<String>();
//        handle the information

        String user = src.substring(0, 31);
        String info = src.substring(32);

        res.add(user);
        res.add(info);

        return res;
    }
}
