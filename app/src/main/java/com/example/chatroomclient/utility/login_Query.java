package com.example.chatroomclient.utility;

import com.example.chatroomclient.Socket.SocketClient;

public class login_Query implements Runnable {
    private String host_addr;
    private String res_uname;
    private String res_upassword;
    private boolean res_return;

    @Override
    public void run(){
        SocketClient client = new SocketClient(host_addr);
        boolean res = client.Login(res_uname,res_upassword);
        System.out.println(res);
        this.res_return = res;
        System.out.println("this res is: " + this.res_return);
    }

    public void setThisQueryInfo(String res_name,String res_password, String host_addr){
        this.res_uname = res_name;
        this.res_upassword = res_password;
        this.host_addr = host_addr;
    }

    public boolean return_res(){
        System.out.println("the return res is: " + this.res_return);
        return this.res_return;
    }
}
