package com.wechat.service;

import com.wechat.common.Message;
import com.wechat.common.MessageType;
import com.wechat.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 服务器推送信息给所有客户端（线程）
 */
public class SendNewsToAllClients implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("请输入要推送的消息：");
            String s = Utility.readString(100);
            Message message = new Message();
            message.setSender("服务器");
            message.setSendTime(LocalDateTime.now());
            message.setMessageType(MessageType.MESSAGE_TO_EVERYONE_MESSAGE);
            message.setContent(s);
            HashMap<String, ServerConnectClientThread> hashMap = ManageConnectionThreads.getHashMap();
            for (ServerConnectClientThread thread : hashMap.values()){
                try {
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(thread.getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(message.getSendTime() + " | 消息已推送~\n\t\t" + s);
        }
    }
}