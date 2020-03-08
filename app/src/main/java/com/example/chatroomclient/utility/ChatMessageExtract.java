package com.example.chatroomclient.utility;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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

    public Queue<String> SplitAllInfoInSocket(String bufsrc){
        Queue<String> res = new ConcurrentLinkedQueue<>();

        String one_message = new String();
        int i = 0, begin = 0, end = 0;
        for (i = 0; i < bufsrc.length(); i++) {
            if (bufsrc.charAt(i) == '\r' || bufsrc.charAt(i) == '\2') {
                // begin
                begin = i;
            } else if (bufsrc.charAt(i) == '\n' || bufsrc.charAt(i) == '\3') {
                // end
                end = i;
            } else {
                continue;
            }

//            have some information.
            if (end >= begin && end != 0){
                one_message = bufsrc.substring(begin + 1, end);
                System.out.println("The one message content is:" + one_message);
                res.offer(one_message);
                begin = end = 0;
            }
        }

        return res;
    }

    public String Substr_blank(String src){
        return src.replace(' ', '\127');
    }

    public String return_blank(String src){
        return src.replace('\127', ' ');
    }
}
