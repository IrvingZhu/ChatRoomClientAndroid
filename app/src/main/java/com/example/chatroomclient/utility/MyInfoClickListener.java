package com.example.chatroomclient.utility;

import android.view.View;

public class MyInfoClickListener implements View.OnClickListener {
    private String uid;
    private String uname;
    private String upassword;
    private int type;

//    for each type, 0 represent myinfo, 1 represent choose, 2 represent join.

    public MyInfoClickListener(String uid, String uname, String upassword, int type){
        this.uid = uid;
        this.uname = uname;
        this.upassword = upassword;
        this.type = type;
    }

    @Override
    public void onClick(View v){

    }
}
