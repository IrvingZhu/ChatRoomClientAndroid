package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatroomclient.utility.JoinRoom;

public class JoinActivity extends AppCompatActivity {
    private EditText chatroom;
    private Button certify;
    private String uid;
    private String uname;

    private void Transfer_JoinActivity(){
//        this.uid = uid;
//        this.uname = uname;
//        this.roomname = roomname;
        final Intent intent = getIntent();
        this.uid = intent.getStringExtra("uid");
        this.uname = intent.getStringExtra("uname");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        this.Transfer_JoinActivity();

        chatroom = (EditText) findViewById(R.id.chatroomname);
        certify = (Button) findViewById(R.id.join);

        certify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomname = JoinActivity.this.chatroom.getText().toString();
                String send_info = "JoinNewChatRoom " + JoinActivity.this.uid + " " + JoinActivity.this.uname + " " + roomname;
                JoinRoom j = new JoinRoom(send_info);
                Thread t = new Thread(j);
                t.start();

                while(t.isAlive());

                boolean res = j.return_res();
                if(res == true){
//                  join and chat;
                    Intent intent = new Intent(JoinActivity.this, ChatActivity.class);
                    intent.putExtra("uid", JoinActivity.this.uid);
                    intent.putExtra("uname", JoinActivity.this.uname);
                    intent.putExtra("roomname", roomname);
                    startActivity(intent);
                }else{
                    Toast.makeText(JoinActivity.this, "加入失败，不存在此聊天室或网络错误" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
