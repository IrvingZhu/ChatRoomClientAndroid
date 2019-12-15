package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.Login);
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                find info from button
                EditText et_uname = findViewById(R.id.UserName);
                EditText et_upassword = findViewById(R.id.Password);
                String res_uname = et_uname.getText().toString();
                String res_upassword = et_upassword.getText().toString();

            }
        });
    }
}
