package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatroomclient.Socket.SocketClient;
import com.example.chatroomclient.utility.modInfo;

public class Myinfo_Activity extends AppCompatActivity {
    private EditText myId;
    private EditText myName;
    private Button modPsw;
    private Button modInfo;
    private String uid;
    private String uname;
    private String upassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_);

        final Intent intent = getIntent();
        this.uid = intent.getStringExtra("uid");
        this.uname = intent.getStringExtra("uname");
        this.upassword = intent.getStringExtra("upassword");

        myId = (EditText) findViewById(R.id.myInfo_Uid);
        myName = (EditText) findViewById(R.id.myInfo_Uname);
        modInfo = (Button) findViewById(R.id.modinfo);
        modPsw = (Button) findViewById(R.id.modpsw);

        myId.setText(this.uid);
        myId.setEnabled(false);
        myName.setText(this.uname);

        modInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.myInfo_Uname);
                if (name == Myinfo_Activity.this.myName) {
                    Toast.makeText(Myinfo_Activity.this, "输入名字与数据库名字一样，无法修改", Toast.LENGTH_LONG).show();
                } else {
                    modInfo m = new modInfo(Myinfo_Activity.this.uid, Myinfo_Activity.this.uname, 0);
                    Thread t = new Thread(m);
                    t.start();

                    while (t.isAlive()) {
                    }

                    boolean res = m.return_result();
                    if (res == true) {
                        Toast.makeText(Myinfo_Activity.this, "修改成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Myinfo_Activity.this, "修改失败，请检查您的网络设置", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        modPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Myinfo_Activity.this, )
            }
        });
    }

    private String completionInfo(String rsc) {
        String res = new String("");
        int length = rsc.length();
        if (length < 12) {
            for (int i = 0; i < 12 - length; i++) {
                res = res + "0";
            }
            res = res + rsc;
        } else return rsc;
        return res;
    }
}
