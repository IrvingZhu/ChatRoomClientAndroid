package com.example.chatroomclient;

public class login_Query extends Thread {
    private String res_uname;
    private String res_upassword;
    private boolean res_return;

    @Override
    public void run(){
        SocketClient client = new SocketClient();
        boolean res = client.Login(res_uname,res_upassword);
        this.res_return = res;
    }

    public void setThisQueryInfo(String res_name,String res_password){
        this.res_uname = res_name;
        this.res_upassword = res_password;
    }

    public boolean return_res(){
        return res_return;
    }
}
