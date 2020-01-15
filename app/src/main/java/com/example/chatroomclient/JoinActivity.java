package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {
    private EditText chatroom;
    private Button certify;
    private String uid;
    private String uname;
    private String roomname;

    private JoinActivity(){
//        this.uid = uid;
//        this.uname = uname;
//        this.roomname = roomname;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        chatroom = (EditText) findViewById(R.id.chatroomname);
        certify = (Button) findViewById(R.id.join);

        certify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
