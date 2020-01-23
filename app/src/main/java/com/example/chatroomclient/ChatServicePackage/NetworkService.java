package com.example.chatroomclient.ChatServicePackage;

import com.example.chatroomclient.utility.ChatMessageExtract;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

// AccessRoom command
// format is "AccessChatRoom [UserName] [ChatName]"

// chat format
// info_res format
// format: "Chat [ChatRoom] [UserName] [Info]"
// [Info] = 4 byte length info + 32 byte of chat userName + 1024 byte of chat words.

public class NetworkService {
    public interface Callback {
        void onConnected(String host, int port);        //连接成功
        void onConnectFailed(String host, int port);    //连接失败
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
    // 套接字输入流对象，从这里读取收到的消息
    private DataInputStream inputStream = null;
    // 套接字输出流对象，从这里发送聊天消息
    private DataOutputStream outputStream = null;
    // 当前连接状态的标记变量
    private boolean isConnected = false;

    /**
     * 连接到服务器
     *  host 服务器地址
     *  port 服务器端口
     */

    private void beginListening() {
        Runnable listening = new Runnable() {
            @Override
            public void run() {
                try {
                    inputStream = new DataInputStream(socket.getInputStream());

                    while (true) {
                        String s = inputStream.readUTF().toString();
                        ChatMessageExtract Chat_util = new ChatMessageExtract();
                        ArrayList<String> result = Chat_util.Extract(s);
                        if (callback != null) {
                            callback.onMessageReceived(result);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        (new Thread(listening)).start();
    }

    public void connect(String host, int port) {
        try {
            // 创建套接字对象，与服务器建立连接
            this.socket = new Socket(host, port);
            this.isConnected = true;
            // 通知外界已连接
            if (this.callback != null) {
                this.callback.onConnected(host, port);
            }
            // 开始侦听是否有聊天消息到来
            this.beginListening();
        } catch (IOException e) {
            // 连接服务器失败
            this.isConnected = false;
            // 通知外界连接失败
            if (callback != null) {
                callback.onConnectFailed(host, port);
            }
            e.printStackTrace();
        }
    }
    /**
     * 断开连接
     */
    public void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (inputStream!= null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
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
     * @return true为已连接，false为未连接
     */
    public boolean isConnected() {
        return isConnected;
    }
    /**
     * 发送聊天消息
     * @param name 用户名
     * @param msg 消息内容
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
            // 将消息写入套接字的输出流
            outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF("Chat " + chatRoom + " " + name + " " + msg);
            outputStream.flush();
            // 通知外界消息已发送
            if (callback != null) {
                callback.onMessageSent(chatRoom, name, msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}