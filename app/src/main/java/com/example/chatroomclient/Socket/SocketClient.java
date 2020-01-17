package com.example.chatroomclient.Socket;

import com.example.chatroomclient.utility.findInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;


public class SocketClient {
    private static Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public boolean Login(String res_uname, String res_upassword) {
        this.socket = new Socket();
        host host = new host();
//        this.socket.connect(new InetSocketAddress(host, port));
        try {
            this.socket.connect(new InetSocketAddress(host.host, host.port));
            String send_info = "Login " + res_uname + " " + res_upassword;
            System.out.println("Prepare to send info "+send_info);

            this.socket.getOutputStream().write(send_info.getBytes("gb2312"));
            System.out.println("Send Successful");

            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String test = this.reader.toString();
            System.out.println(test);
            String response = reader.readLine();
            System.out.println(response);

            System.out.println(this.socket.isClosed());

            int posi = response.indexOf("/");
            System.out.println(posi);
            String cpy_str = response.substring(0, posi);
            System.out.println(cpy_str);

            if (cpy_str.compareTo("SuccessLogin") == 0) {
                this.socket.close();
                return true;
            } else {
                this.socket.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    public boolean Register(String u_name, String u_password){
        this.socket = new Socket();
        host host = new host();
        try{
            this.socket.connect(new InetSocketAddress(host.host, host.port));
            String send_info = "Register " + u_name + " " + u_password;
            System.out.println("Prepare to send info "+send_info);

            this.socket.getOutputStream().write(send_info.getBytes("gb2312"));
            System.out.println("Send Successful");

            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String response = reader.readLine();
            System.out.println(response);

            System.out.println(this.socket.isClosed());

            int posi = response.indexOf("/");
            System.out.println(posi);
            String cpy_str = response.substring(0, posi);
            System.out.println(cpy_str);

            if(cpy_str.compareTo("SuccessRegister") == 0){
                this.socket.close();
                return true;
            }else{
                this.socket.close();
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<String> searchAllRoom(String uid){
        this.socket = new Socket();
        host host = new host();

        try{
            this.socket.connect(new InetSocketAddress(host.host, host.port));
            String send_info = "SearchUserAllJoinedRoom " + uid;

            this.socket.getOutputStream().write(send_info.getBytes("gb2312"));
            System.out.println("Prepare to send info "+send_info);

            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

//            char medi[] = new char[128];
//            reader.read(medi, 0, medi.length);
//            String room_info = new String(medi);
//            while(!finished){
            String response = reader.readLine();
            System.out.println(response);
//
            String room_info = new String("");
            if(response.indexOf("/") == -1){
                room_info = room_info + response;
            }else{
                int posi = response.indexOf("/");
                room_info = response.substring(0, posi);
            }
//
//            System.out.println(room_info);
//            }
//
//            room_info = room_info.substring(0, room_info.length()-1);
            System.out.println(room_info);

            findInfo f = new findInfo();
            ArrayList<String> res = f.findAllInfo(room_info);

            return res;

        }catch(Exception e){
            e.printStackTrace();
            ArrayList<String> res = new ArrayList<String>();
            return res;
        }
    }

    public ArrayList<String> searchAllUserInfo(String uname){
        this.socket = new Socket();
        host host = new host();

        try {
            this.socket.connect(new InetSocketAddress(host.host, host.port));
            String send_info = "SearchUserAllInfo " + uname;
            System.out.println("Prepare to send info " + send_info);

            this.socket.getOutputStream().write(send_info.getBytes("gb2312"));
            System.out.println("Send Successful");

            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            String response = reader.readLine();
            System.out.println(response);

            String user_info = new String("");
            if(response.indexOf("/") == -1){
                user_info = user_info + response;
            }else{
                int posi = response.indexOf("/");
                user_info = response.substring(0, posi);
            }

            System.out.println(user_info);

            System.out.println(this.socket.isClosed());

            findInfo f = new findInfo();
            System.out.println(user_info);
            ArrayList<String> res = f.findAllInfo(user_info);
            ArrayList<String> result = new ArrayList<String>();

            for(int i = 1; i < res.size(); i++){
                result.add(res.get(i));
            }

            return result;

        }catch(Exception e){
            e.printStackTrace();
            ArrayList<String> result = new ArrayList<String>();
            return result;
        }
    }

    public boolean modifyInfo(String uid, String rsc, int type){
//        type 0 is uname;
//        type 1 is upassword;
        this.socket = new Socket();
        host host = new host();

        try {
            this.socket.connect(new InetSocketAddress(host.host, host.port));
            switch (type) {
                case 0:
//                    Modify [Uid] [Uname]
                    String modname_send_info = "Modify " + uid + " " + rsc;
                    System.out.println("Prepare to send info " + modname_send_info);

                    this.socket.getOutputStream().write(modname_send_info.getBytes("gb2312"));
                    System.out.println("Send Successful");

                    break;
                case 1:
//                    ModPsw [Uid] [password]
                    String modpsw_send_info = "ModPsw " + uid + " " + rsc;
                    System.out.println("Prepare to send info " + modpsw_send_info);

                    this.socket.getOutputStream().write(modpsw_send_info.getBytes("gb2312"));
                    System.out.println("Send Successful");

                    break;
                default:
                    return false;
            }

            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            String response = reader.readLine();
            System.out.println(response);

            String return_info = new String("");
            if(response.indexOf("/") == -1){
                return_info = response;
            }else{
                int posi = response.indexOf("/");
                return_info = response.substring(0, posi);
                System.out.println(return_info);
            }

            if(return_info.compareTo("SuccessModify") == 0 || return_info.compareTo("SuccessModPsw") == 0){
                return true;
            }else return false;


        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean joinedRoom(String send_info){
        this.socket = new Socket();
        host host = new host();

        try {
            this.socket.connect(new InetSocketAddress(host.host, host.port));
            System.out.println("Prepare to send info " + send_info);

            this.socket.getOutputStream().write(send_info.getBytes("gb2312"));
            System.out.println("Send Successful");

            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            String response = reader.readLine();
            System.out.println(response);

            String return_info = new String("");
            if(response.indexOf("/") == -1){
                return_info = response;
            }else{
                int posi = response.indexOf("/");
                return_info = response.substring(0, posi);
                System.out.println(return_info);
            }

            if(return_info.compareTo("SuccessJoin") == 0){
                return true;
            }else return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean createRoom(String send_info){
        this.socket = new Socket();
        host host = new host();

        try {
            this.socket.connect(new InetSocketAddress(host.host, host.port));
            System.out.println("Prepare to send info " + send_info);

            this.socket.getOutputStream().write(send_info.getBytes("gb2312"));
            System.out.println("Send Successful");

            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            String response = reader.readLine();
            System.out.println(response);

            String return_info = new String("");
            if(response.indexOf("/") == -1){
                return_info = response;
            }else{
                int posi = response.indexOf("/");
                return_info = response.substring(0, posi);
                System.out.println(return_info);
            }

            if(return_info.compareTo("SuccessCre") == 0){
                return true;
            }else return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
