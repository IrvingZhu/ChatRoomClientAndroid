package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button Login;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.Login);
        Register = findViewById(R.id.Register);
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                find info from button
                EditText et_uname = findViewById(R.id.UserName);
                EditText et_upassword = findViewById(R.id.Password);
                String res_uname = et_uname.getText().toString();
                String res_upassword = et_upassword.getText().toString();
                new Thread(new Runnable(res_uname,res_upassword) {
                    @Override
                    public void run() {
                        SocketClient client = new SocketClient();
                        boolean res = client.Login(res_uname,res_upassword);
                    }
                });
                if(res == true){
//                    goto new page
                    Intent intent = new Intent();
                }else{
//                    fault, put up a new reminder into page
//                    and set the edit text is null.
                    et_uname.setText("");
                    et_upassword.setText("");
                    Toast.makeText(MainActivity.this, "登录失败,用户名或密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
