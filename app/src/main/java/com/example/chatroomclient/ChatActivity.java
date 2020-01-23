package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatroomclient.ChatServicePackage.NetworkService;
import com.example.chatroomclient.Chatutil.ChatAdapter;
import com.example.chatroomclient.Chatutil.PersonChat;
import com.example.chatroomclient.Socket.host;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private NetworkService networkService;
    private ChatAdapter adapter;
    private ListView lv_chat_dialog;
    private String host;
    private int port;
    private Button send;
    private String this_room;
    private String name;
    private String message;
    private List<PersonChat> personChats = new ArrayList<PersonChat>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        host host = new host();
        this.host = host.host;
        this.port = host.port;

        this.initNetworkService();

        send = (Button) findViewById(R.id.btn_chat_message_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText info_input = (EditText) findViewById(R.id.et_chat_message);
                lv_chat_dialog = (ListView) findViewById(R.id.lv_chat_dialog);

                String send_info = info_input.getText().toString();
                if(send_info.isEmpty() == true){
                    Toast.makeText(ChatActivity.this, "发送内容不能为空", Toast.LENGTH_LONG).show();
                }else{
                    ChatActivity.this.networkService.sendMessage(ChatActivity.this.this_room, ChatActivity.this.name, send_info);
                }
            }
        });
    }

    private void initNetworkService(){
        networkService = new NetworkService();
        networkService.setCallback(new NetworkService.Callback(){
            @Override
            public void onConnected(String host, int port){
                System.out.println("The client has connected to the " + host + ":" + port);
                Toast.makeText(ChatActivity.this, "连接成功，您可以开始聊天了", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectFailed(String host, int port){
                System.out.println("The client has failed to connect to server");
                Toast.makeText(ChatActivity.this, "连接失败，请检查网络设置", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDisconnected(){
                System.out.println("The client is disconnecting...");
            }

            @Override
            public void onMessageSent(String RoomName, String name, String msg){
                System.out.println(name + " send info " + msg);
//                the item add your send info
            }

            @Override
            public void onMessageReceived(ArrayList<String> res){
//                the item add your receive info
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK && this.networkService.isConnected()){
            networkService.disconnect();
            return true;
        }else return false;
    }
}
