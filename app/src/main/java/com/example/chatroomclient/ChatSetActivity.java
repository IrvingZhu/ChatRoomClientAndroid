package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatroomclient.utility.MyInfoClickListener;
import com.example.chatroomclient.utility.searchAllRoom;
import com.example.chatroomclient.utility.searchAllUserInfo;

import java.util.ArrayList;

public class ChatSetActivity extends AppCompatActivity {

    private String uid;
    private String uname;
    private String upassword;

    private ImageView myInfo;
    private ImageView choose;
    private ImageView join;

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
        int res_num = res.size(), count = 0;
        while(count < res_num){
            LinearLayout ll = (LinearLayout)findViewById(R.id.SLineLayout);
            TextView tv = new TextView(this);
            tv.setText(res.get(count));
            ll.addView(tv);
            count++;
        }

        myInfo = findViewById(R.id.myinfo);
        choose = findViewById(R.id.choose);
        join = findViewById(R.id.join);

        MyInfoClickListener m_myinfo = new MyInfoClickListener(this.uid, this.uname, this.upassword, 0);
        MyInfoClickListener m_choose = new MyInfoClickListener(this.uid, this.uname, this.upassword, 1);
        MyInfoClickListener m_join = new MyInfoClickListener(this.uid, this.uname, this.upassword, 2);

        myInfo.setOnClickListener(m_myinfo);
        choose.setOnClickListener(m_choose);
        join.setOnClickListener(m_join);
    }
}
