package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView return_btn;
    private String this_room;
    private String uid;
    private String name;
    private String psw_temp; // this is not to use
    private List<PersonChat> personChats = new ArrayList<PersonChat>();

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    lv_chat_dialog.setSelection(personChats.size());
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        final Intent intent = getIntent();
        this.uid = intent.getStringExtra("uid");
        this.name = intent.getStringExtra("uname");
        this.this_room = intent.getStringExtra("roomname");
        this.psw_temp = intent.getStringExtra("upassword");
        this.adapter = new ChatAdapter(this, personChats);

        host host = new host();
        this.host = host.host;
        this.port = host.port;

        this.initNetworkService();
        this.networkService.connect(this.host, this.port, this.name, this.this_room);

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
        this.return_btn = findViewById(R.id.return_button);
        this.return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChatActivity.this.networkService.isConnected()){
                    networkService.leave(ChatActivity.this.this_room);
                    Intent intent = new Intent(ChatActivity.this, ChatSetActivity.class);
                    intent.putExtra("uid", ChatActivity.this.uid);
                    intent.putExtra("uname", ChatActivity.this.name);
                    intent.putExtra("upassword", ChatActivity.this.psw_temp);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ChatActivity.this, ChatSetActivity.class);
                    intent.putExtra("uid", ChatActivity.this.uid);
                    intent.putExtra("uname", ChatActivity.this.name);
                    intent.putExtra("upassword", ChatActivity.this.psw_temp);
                    startActivity(intent);
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
                Toast.makeText(ChatActivity.this, "聊天室已连接到 " + host + ":" + port, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectFailed(){
                System.out.println("The client has failed to connect to server");
                Toast.makeText(ChatActivity.this, "连接失败，请检查网络设置", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDisconnected(){
                System.out.println("The client is disconnecting...");
            }

            @Override
            public void onMessageSent(String RoomName, String name, String msg){
                System.out.println(name + " send info :" + msg);
//                the item add your send info
                PersonChat p = new PersonChat();
                p.setMeSend(true);
                ChatActivity.this.personChats.add(p);

                p.setName(name);
                p.setChatMessage(msg);

                EditText info_input = (EditText) findViewById(R.id.et_chat_message);
                info_input.setText("");

                adapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onMessageReceived(ArrayList<String> res){
                if(res.size() == 1) Toast.makeText(ChatActivity.this, res.get(0), Toast.LENGTH_LONG).show();
                else{
                    System.out.println(res.get(1) + " receive info " + res.get(2));
//                the item add your receive info
                    PersonChat p = new PersonChat();
                    p.setMeSend(false);
                    ChatActivity.this.personChats.add(p);

                    p.setName(res.get(1));
                    p.setChatMessage(res.get(2));

                    adapter.notifyDataSetChanged();
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }
}
