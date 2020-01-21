package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chatroomclient.ChatServicePackage.NetworkService;
import com.example.chatroomclient.Socket.host;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private NetworkService networkService;
    private String host;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        host host = new host();
        this.host = host.host;
        this.port = host.port;
    }

    private void initNetworkService(){
        networkService = new NetworkService();
        networkService.setCallback(new NetworkService.Callback(){
            @Override
            public void onConnected(String host, int port){
                
            }

            @Override
            public void onConnectFailed(String host, int port){

            }

            @Override
            public void onDisconnected(){

            }

            @Override
            public void onMessageSent(String RoomName, String name, String msg){

            }

            @Override
            public void onMessageReceived(ArrayList<String> res){

            }
        });
    }
}
