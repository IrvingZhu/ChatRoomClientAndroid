package com.example.chatroomclient;

import java.util.ArrayList;

public class findInfo {
    public ArrayList<String> findAllInfo(String info){
        ArrayList<String> res = new ArrayList<String>();
        if(info.length() == 0) return res;
        int start = 0, end = 0;
        while(info.length() != 0){
            int posi = info.compareTo(" ");
            end = posi;
            String each_res = info.substring(start, end);
            res.add(each_res);
            start = end;
            info = info.substring(start);
            start = 0;
            end = 0;
        }
        return res;
    }
}
