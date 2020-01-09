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

public class ChatSetActivity extends AppCompatActivity {

    private String uid;
    private String uname;
    private String upassword;

    private ImageView myInfo;
    private ImageView choose;
    private ImageView join;

    public void Transfer_ChatSetActivity(){
        final Intent intent = getIntent();
        this.uname = intent.getStringExtra("uname");
        this.upassword = intent.getStringExtra("upassword");

        searchAllUserInfo search_client = new searchAllUserInfo(this.uname);
        Thread t1 = new Thread(search_client);
        t1.start();
        while(t1.isAlive());
        ArrayList<String> res = search_client.return_userinfo();
        this.uid = res.get(0);

        for(int i = 0; i < res.size(); i++){
            System.out.println(res.get(i));
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

        ScrollView sc = (ScrollView)findViewById(R.id.chatset);
        ArrayList<String> res = search_client.return_roomset();
        LinearLayout ll = (LinearLayout)findViewById(R.id.SLineLayout);
        TextView top_TextView = (TextView)findViewById(R.id.textView);
        LinearLayout bl = (LinearLayout)findViewById(R.id.bottom_ll);

        int resofScreen[] = this.getAndroidScreenProperty();
        int screen_Height = resofScreen[1];

        ViewGroup.LayoutParams lp = sc.getLayoutParams();
        lp.height = screen_Height;

        System.out.println("the screen height size is: " + screen_Height);
        System.out.println("the sc height size is: " +  lp.height);

        sc.setLayoutParams(lp);

        int res_num = res.size(), count = 1;
        while(count < res_num){
            TextView tv = new TextView(this);
            tv.setText(res.get(count));
            tv.setTextSize(50);
            ll.addView(tv);
            count++;
        }

        myInfo = findViewById(R.id.myinfo);
        choose = findViewById(R.id.choose);
        join = findViewById(R.id.join);

        myInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ChatSetActivity.this, Myinfo_Activity.class);
                startActivity(intent);
            }
        });

        choose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ChatSetActivity.this, SearchRoom_Activity.class);
                startActivity(intent);
            }
        });

        join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Intent intent = new Intent(ChatSetActivity.this, )
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
}
