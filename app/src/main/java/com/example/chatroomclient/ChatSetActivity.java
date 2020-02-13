package com.example.chatroomclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chatroomclient.utility.searchAllRoom;
import com.example.chatroomclient.utility.searchAllUserInfo;

import java.util.ArrayList;
import java.util.Map;

public class ChatSetActivity extends AppCompatActivity {

    private String uid;
    private String uname;
    private String upassword;
    private ImageView myInfo;
    private ImageView create;
    private ImageView join;

    private class tv_Listener implements View.OnClickListener{
        private String Room_String;

        public tv_Listener(String tv_name){
            this.Room_String = tv_name;
        }

        @Override
        public void onClick(View v){
            Intent intent = new Intent(ChatSetActivity.this, ChatActivity.class);
            intent.putExtra("uid", ChatSetActivity.this.uid);
            intent.putExtra("uname", ChatSetActivity.this.uname);

            intent.putExtra("roomname", Room_String);
//                    contemporary variable to chat room
            intent.putExtra("upassword",ChatSetActivity.this.upassword);
            startActivity(intent);
        }
    }

    private void Transfer_ChatSetActivity(){
        final Intent intent = getIntent();
        this.uid = intent.getStringExtra("uid");
        this.uname = intent.getStringExtra("uname");
        this.upassword = intent.getStringExtra("upassword");

        searchAllUserInfo search_client = new searchAllUserInfo(this.uname);
        Thread t1 = new Thread(search_client);
        t1.start();
        while(t1.isAlive());
        ArrayList<String> res = search_client.return_userinfo();
        if(res.size() != 0) {
            this.uid = res.get(0);
            for(int i = 0; i < res.size(); i++){
                System.out.println(res.get(i));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_set);

        Transfer_ChatSetActivity();

        searchAllRoom search_client = new searchAllRoom(this.uid);
        Thread t1 = new Thread(search_client);
        t1.start();
        while(t1.isAlive());

        ArrayList<String> res = search_client.return_roomset();

        ScrollView sc = (ScrollView)findViewById(R.id.chatset);
        LinearLayout ll = (LinearLayout)findViewById(R.id.SLineLayout);
        TextView top_TextView = (TextView)findViewById(R.id.textView);
        LinearLayout bl = (LinearLayout)findViewById(R.id.bottom_ll);

//        screen setting
        int resofScreen[] = this.getAndroidScreenProperty();
        int screen_Height = resofScreen[1];

        System.out.println("the Height of screen is : " + screen_Height);

        ViewGroup.LayoutParams lp = bl.getLayoutParams();
        ViewGroup.LayoutParams sc_lp = sc.getLayoutParams();
        System.out.println("the lp height is :" + lp.height);
        sc_lp.height = sc_lp.height - lp.height - 80;

        System.out.println("the screen height size is: " + screen_Height);
        System.out.println("the sc height size is: " +  sc_lp.height);

        sc.setLayoutParams(sc_lp);
//        end

        int res_num = res.size();
        int count = 1;
        while(count < res_num){
            TextView tv = new TextView(this);
            tv.setText(res.get(count));
            tv.setTextSize(50);
            ll.addView(tv);
            tv.setOnClickListener(new tv_Listener(tv.getText().toString()));
            count++;
        }

        myInfo = findViewById(R.id.myinfo);
        join = findViewById(R.id.join);
        create = findViewById(R.id.create);

        myInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ChatSetActivity.this, Myinfo_Activity.class);
                intent.putExtra("uid",ChatSetActivity.this.uid);
                intent.putExtra("uname",ChatSetActivity.this.uname);
                intent.putExtra("upassword",ChatSetActivity.this.upassword);
                ChatSetActivity.this.startActivity(intent);
            }
        });

        join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ChatSetActivity.this, JoinActivity.class);
                intent.putExtra("uid", ChatSetActivity.this.uid);
                intent.putExtra("uname", ChatSetActivity.this.uname);
                intent.putExtra("upassword",ChatSetActivity.this.upassword);
                startActivity(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatSetActivity.this, createRoom.class);
                intent.putExtra("uid", ChatSetActivity.this.uid);
                intent.putExtra("uname", ChatSetActivity.this.uname);
                intent.putExtra("upassword",ChatSetActivity.this.upassword);
                startActivity(intent);
            }
        });
    }

    public int[] getAndroidScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)

        int resofscreen[] = new int[2];
        resofscreen[0] = screenWidth;
        resofscreen[1] = screenHeight;

        return resofscreen;
    }

    public int retComponentDpSize(ViewGroup.LayoutParams lp, int type){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

//        0 represent height,1 represent width
        if(type == 0){
            int lp_height = lp.height;
            float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）

            int component_Size = (int) (lp_height / density);
            return component_Size;
        }else if(type == 1){
            int lp_width = lp.width;
            float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）

            int component_Size = (int) (lp_width / density);
            return component_Size;
        }else{
            return 0;
        }
    }
}
