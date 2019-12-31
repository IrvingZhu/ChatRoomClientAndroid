package com.example.chatroomclient;

//import Runnable;

import com.example.chatroomclient.Socket.SocketClient;

public class RegisterActivity implements Runnable {

    private String u_name;
    private String u_password;
    private boolean res_return;

    public RegisterActivity(String u_name, String u_password){
        this.u_name = u_name;
        this.u_password = u_password;
    }

    @Override
    public void run(){
        SocketClient client = new SocketClient();
        boolean res = client.Register(this.u_name,this.u_password);
        System.out.println(res);
        this.res_return = res;
        System.out.println("this res is: " + this.res_return);
    }

    public boolean return_res(){
        System.out.println("this res is: " + this.res_return);
        return this.res_return;
    }

}
