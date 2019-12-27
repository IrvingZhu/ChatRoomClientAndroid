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

//                query to server database
                login_Query query = new login_Query();
                query.setThisQueryInfo(res_uname, res_upassword);
                Thread t1 = new Thread(query);
                t1.start();
//                the thread is alive,if ture,wait for this thread.
                while(t1.isAlive()){}

                boolean res = query.return_res();
                System.out.println("the final res is: " + res);

                if(res == true){
//                    goto new page
                    System.out.println("Jump to next page...");
                    Toast.makeText(MainActivity.this, "登录成功，正在跳转...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, ChatSetActivity.class);
                }else{
//                    fault, put up a new reminder into page
//                    and set the edit text is null.
                    et_uname.setText("");
                    et_upassword.setText("");
                    Toast.makeText(MainActivity.this, "登录失败,用户名或密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, register_Activity.class);
                startActivity(intent);
            }
        });
    }
}
