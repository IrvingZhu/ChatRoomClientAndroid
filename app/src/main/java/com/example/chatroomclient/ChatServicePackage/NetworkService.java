package com.example.chatroomclient.ChatServicePackage;

import android.os.Build;
import android.os.StrictMode;

import com.example.chatroomclient.utility.ChatMessageExtract;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

// AccessRoom command
// format is "AccessChatRoom [UserName] [ChatName]"

// chat format
// info_res format
// format: "Chat [ChatRoom] [UserName] [Info]"
// [Info] = 4 byte length info + 32 byte of chat userName + 1024 byte of chat words.

public class NetworkService {
    public interface Callback {
        void onConnected(String host, int port);        //连接成功

        void onConnectFailed();    //连接失败

        void onDisconnected();                          //已经断开连接

        void onMessageSent(String RoomName, String name, String msg);    //消息已经发出

        void onMessageReceived(ArrayList<String> res);//收到消息
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    // 套接字对象
    private Socket socket = null;
    // 套接字输出流对象，从这里读取收到的消息
    private BufferedReader in = null;
    // 当前连接状态的标记变量
    private boolean isConnected = false;
    // 内部读消息类
    private class ReceiveThread extends Thread{
        @Override
        public void run() {
            try {
                //轮询
                while (true) {
                    char recvbuf[] = new char[1536];
                    in.read(recvbuf);
                    String s = new String(recvbuf);

                    System.out.println("the content has been readed:" + s);

                    ChatMessageExtract Chat_util = new ChatMessageExtract();

                    Queue<String> output = Chat_util.SplitAllInfoInSocket(s);
                    while(output.size() != 0){
                        String output_message = output.poll();
                        if(output_message.compareTo("SuccessAccess") == 0){
                            //连接成功
                            String host = socket.getLocalSocketAddress().toString();
                            int port = socket.getPort();
                            callback.onConnected(host, port);
                        }else if(output_message.compareTo("Exist?") == 0){
                            //心跳检测不管
                            continue;
                        }else{
                            //聊天消息发出
                            ArrayList<String> result = Chat_util.Extract(output_message);
                            callback.onMessageReceived(result);
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    // 读消息线程
    private ReceiveThread recvThd;

    //消息队列
    private Queue<String> write_queue;
    //消息队列锁
    private static Object LOCK = true;

    private class SendThread extends Thread{
        @Override
        public void run(){
            try{
                while(true){
                    synchronized (LOCK){
                        try{
                            while(write_queue.size() == 0)
                                LOCK.wait();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        String send_info = write_queue.poll();
                        System.out.println("write info to server:" + send_info);
                        socket.getOutputStream().write(send_info.getBytes("gb2312"));

                        LOCK.notifyAll();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    // 写消息线程
    private SendThread sendThd;

    private class startThread extends Thread{
        private String host;
        private int port;
        private String userName;
        private String RoomName;

        public startThread(String host, int port, String userName, String RoomName){
            this.host = host;
            this.port = port;
            this.userName = userName;
            this.RoomName = RoomName;
        }

        @Override
        public void run(){
            try {
                // 创建套接字对象，与服务器建立连接
                socket = new Socket(host, port);
                isConnected = true;
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "gb2312"));

                System.out.println(socket.getLocalAddress() + ":" + socket.getLocalPort() + " sending...");
                String send_info = "AccessChatRoom " + userName + " " + RoomName;
                socket.getOutputStream().write(send_info.getBytes("gb2312"));

                // 启动读写线程
                sendThd = new SendThread();
                sendThd.start();

                recvThd = new ReceiveThread();
                recvThd.start();

            } catch (IOException e) {
                // 连接服务器失败
                isConnected = false;
                // 通知外界连接失败
                if (callback != null) {
                    callback.onConnectFailed();
                }
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接到服务器
     * host 服务器地址
     * port 服务器端口
     */

    public void connect(String host, int port, String userName, String RoomName) {
        // 构造消息队列
        write_queue = new ConcurrentLinkedQueue<>();
        // 连接到服务器
        startThread st = new startThread(host, port, userName, RoomName);
        st.start();
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }
            isConnected = false;
            // 通知外界连接断开
            if (callback != null) {
                callback.onDisconnected();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否已经连接到服务器
     *
     * @return true为已连接，false为未连接
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * 发送聊天消息
     *
     * @param name 用户名
     * @param msg  消息内容
     */
    public void sendMessage(String chatRoom, String name, String msg) {
        // 检查参数合法性
        if (chatRoom.compareTo("") == 0 || name == null || "".equals(name) || msg == null || "".equals(msg)) {
            return;
        }
        if (socket == null) {   //套接字对象必须已创建
            return;
        }

        try {
            // 构造消息
            ChatMessageExtract Chat_util = new ChatMessageExtract();
            String newMsg = Chat_util.Substr_blank(msg);
            String send_info = "Chat " + chatRoom + " \r" + name + " " + newMsg + "\n\0\0";
            System.out.println("Message is:" + send_info);

            // 将消息写入消息队列
            synchronized (LOCK){
                write_queue.offer(send_info); // 往队列中写消息
                System.out.println("the message is push back to message queue");

                LOCK.notifyAll();
            }

            // 通知外界消息已发送
            if (callback != null) {
                callback.onMessageSent(chatRoom, name, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void leave(String chatRoom) {
        if(socket == null)
            return;

        try{
            String send_info = "Leave " + chatRoom;
            System.out.println("Prepare to leave " + chatRoom);
            synchronized(LOCK){
                write_queue.offer(send_info);
                System.out.println("the message is push back to message queue");

                LOCK.notifyAll();
            }
            System.out.println("Send successful");

            this.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
