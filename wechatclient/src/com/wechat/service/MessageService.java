package com.wechat.service;

import com.wechat.common.Message;
import com.wechat.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

/**
 * 文本信息相关的服务
 */
public class MessageService {
    /**
     * 私聊消息
     * @param receiver 私聊对象
     * @param sender 发送者
     * @param content 消息内容
     * @param sendTime 发送时间
     */
    public static void sendMessageToPersonal(String receiver, String sender, String content, LocalDateTime sendTime) {
        Message message = new Message();
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setContent(content);
        message.setMessageType(MessageType.MESSAGE_TO_PERSONAL_MESSAGE);
        message.setSendTime(sendTime);
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ManageConnectionThreads
                            .getClientConnectServerThread(sender).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 群发消息
     * @param sender 发送者
     * @param content 消息内容
     * @param sendTime 发送时间
     */
    public static void sendMessageToEveryone(String sender, String content, LocalDateTime sendTime) {
        Message message = new Message();
        message.setSender(sender);
        message.setSendTime(sendTime);
        message.setMessageType(MessageType.MESSAGE_TO_EVERYONE_MESSAGE);
        message.setContent(content);
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ManageConnectionThreads
                            .getClientConnectServerThread(sender).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 私发文件
     * @param receiver 接收者
     * @param sender 发送者
     * @param filePath 文件路径
     * @param sendTime 发送时间
     */
    public static void sendFileToPersonal(String receiver, String sender, String filePath, LocalDateTime sendTime) {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_TO_PERSONAL_FILE);
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setSendTime(sendTime);
        
    }
}
