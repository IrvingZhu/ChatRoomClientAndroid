package com.example.chatroomclient.utility;

import java.util.ArrayList;

public class ChatMessageExtract {

    public ArrayList<String> Extract(String src){
        ArrayList<String> res = new ArrayList<String>();
//        handle the information

        src = this.return_blank(src);

        int posi = src.indexOf(':');
        String user = src.substring(0, posi);
        String info = src.substring(posi + 1);

        res.add(user);
        res.add(info);

        return res;
    }

    public String Substr_blank(String src){
        return src.replace(' ', '\127');
    }

    public String return_blank(String src){
        return src.replace('\127', ' ');
    }
}
