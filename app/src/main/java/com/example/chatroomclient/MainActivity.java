package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;

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
//                first,send a socket to confirm.

            }
        });
    }
}
