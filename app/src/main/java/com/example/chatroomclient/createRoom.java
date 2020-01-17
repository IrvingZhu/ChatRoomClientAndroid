package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatroomclient.utility.createRoom_Search;

public class createRoom extends AppCompatActivity {
    private Button certify;
    private String uid;
    private String uname;

    private void setThisRoomInfo(){
        final Intent intent = getIntent();
        this.uid = intent.getStringExtra("uid");
        this.uname = intent.getStringExtra("uname");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        this.setThisRoomInfo();

        this.certify = (Button) findViewById(R.id.join);
        certify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed = (EditText) findViewById(R.id.chatroomname);
                String roomname = ed.getText().toString();
                String send_info = "CreateChatRoom " + createRoom.this.uid + " " + createRoom.this.uname + " " + roomname;
                System.out.println("send_info information is: " + send_info);
                createRoom_Search c = new createRoom_Search(send_info);
                Thread t = new Thread(c);
                t.start();

                while(t.isAlive());
                boolean res = c.return_res();

                if(res == true){
                    Intent intent = new Intent(createRoom.this, ChatActivity.class);
                    intent.putExtra("uid", createRoom.this.uid);
                    intent.putExtra("uname", createRoom.this.uname);
                    intent.putExtra("roomname", roomname);
                    startActivity(intent);
                }else{
                    Toast.makeText(createRoom.this, "创建失败，不存在此聊天室或网络错误" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
