package com.example.chatroomclient.Chatutil;

public class PersonChat {
    /**
     * 姓名
     */
    private String name;
    /**
     * 聊天内容
     */
    private String chatMessage;
    /**
     *
     * @return 是否为本人发送
     */
    private boolean isMeSend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public boolean isMeSend() {
        return isMeSend;
    }

    public void setMeSend(boolean isMeSend) {
        this.isMeSend = isMeSend;
    }

    public PersonChat(String name, String chatMessage, boolean isMeSend) {
        super();
        this.name = name;
        this.chatMessage = chatMessage;
        this.isMeSend = isMeSend;
    }

    public PersonChat() {
        super();
    }
}
