package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ChatSetActivity extends AppCompatActivity {

    private String uid;
    private String uname;
    private String upassword;

    public ChatSetActivity(String uname, String upassword){
//        this.uname = uname;
//        this.upassword = upassword;
        final Intent intent = getIntent();
        this.uname = intent.getStringExtra("uname");
        this.upassword = intent.getStringExtra("upassword");

        searchAllUserInfo search_client = new searchAllUserInfo();
        Thread t1 = new Thread(search_client);
        t1.start();
        while(t1.isAlive());
        ArrayList<String> res = search_client.return_userinfo();
        this.uid = res.get(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_set);

        searchAllRoom search_client = new searchAllRoom(this.uid);
        Thread t1 = new Thread(search_client);
        t1.start();
        while(t1.isAlive());
        ArrayList<String> res = search_client.return_roomset();
    }
}
