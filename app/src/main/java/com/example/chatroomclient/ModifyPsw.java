package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatroomclient.utility.modInfo;

public class ModifyPsw extends AppCompatActivity {

    private EditText prePsw;
    private EditText newPsw;
    private EditText newPsw2;
    private Button certify;
    private String uid;
    private String upassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);

        this.certify = (Button) findViewById(R.id.certifyMod);
        final Intent intent = getIntent();
        this.uid = intent.getStringExtra("uid");
        this.upassword = intent.getStringExtra("upassword");
        certify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prePsw = (EditText) findViewById(R.id.sourcePsw);
                newPsw = (EditText) findViewById(R.id.newPsw);
                newPsw2 = (EditText) findViewById(R.id.newPsw2);

                String S_prePsw = prePsw.getText().toString();
                String S_newPsw = newPsw.getText().toString();
                String S_newPsw2 = newPsw2.getText().toString();

                if(S_prePsw.compareTo(ModifyPsw.this.upassword) == 0){
                    if(S_prePsw.compareTo(S_newPsw) == 0){
                        Toast.makeText(ModifyPsw.this, "原密码与新密码一致", Toast.LENGTH_LONG).show();
                        prePsw.setText("");
                        newPsw.setText("");
                        newPsw2.setText("");
                    }else if(S_newPsw.compareTo(S_newPsw2) != 0){
                        Toast.makeText(ModifyPsw.this, "两次新密码输入不一致，请重新输入", Toast.LENGTH_LONG).show();
                        newPsw.setText("");
                        newPsw2.setText("");
                    }else{
                        modInfo m = new modInfo(ModifyPsw.this.uid, S_newPsw, 1);
                        Thread t = new Thread(m);
                        t.start();

                        while (t.isAlive()) {
                        }

                        boolean res = m.return_result();
                        if (res == true) {
                            Toast.makeText(ModifyPsw.this, "修改成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ModifyPsw.this, "修改失败，请检查您的网络设置", Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(ModifyPsw.this, "修改失败，原密码错误", Toast.LENGTH_LONG).show();
                    prePsw.setText("");
                }
            }
        });
    }
}
