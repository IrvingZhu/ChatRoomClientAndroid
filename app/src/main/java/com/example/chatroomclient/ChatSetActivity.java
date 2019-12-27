package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChatSetActivity extends AppCompatActivity {

    private String uid;
    private String uname;
    private String upassword;

    public ChatSetActivity(String uid, String uname, String upassword){
        this.uid = uid;
        this.uname = uname;
        this.upassword = upassword;
    }

    public searchAllUserInfo(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_set);


    }
}
