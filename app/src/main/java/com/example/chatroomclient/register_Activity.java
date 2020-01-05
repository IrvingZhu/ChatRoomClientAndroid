package com.example.chatroomclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatroomclient.utility.RegisterActivity;

public class register_Activity extends AppCompatActivity {

    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_interface);
        register = findViewById(R.id.regis_check);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText etuname = findViewById(R.id.username);
                EditText etupassword1 = findViewById(R.id.upassword1);
                EditText etupassword2 = findViewById(R.id.upassword2);
                String u_name = etuname.getText().toString();
                String u_password1 = etupassword1.getText().toString();
                String u_password2 = etupassword2.getText().toString();

                if(u_password1.compareTo(u_password2) == 0){
                    RegisterActivity recon = new RegisterActivity(u_name, u_password1);
                    Thread t1 = new Thread(recon);

                    t1.start();
//                    the thread is alive,if ture,wait for this thread.
                    while(t1.isAlive()){}

                    boolean res = recon.return_res();
                    System.out.println("the final res is: " + res);

                    if(res == true){
                        System.out.println("Jump to Login page...");
                        Toast.makeText(register_Activity.this, "注册成功，正在跳转回登录界面...", Toast.LENGTH_LONG).show();

                        try{
                            Thread.sleep(3000);
                            Intent intent = new Intent(register_Activity.this, MainActivity.class);
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }else{
                        Toast.makeText(register_Activity.this, "注册失败，请稍候再试...", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(register_Activity.this, "两次输入密码不一致，请重新输入...", Toast.LENGTH_LONG).show();
                    etupassword1.setText("");
                    etupassword2.setText("");
                }
            }
        });
    }
}
