package com.wechat.service;

import com.wechat.common.Message;
import com.wechat.common.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

/**
 * 文件相关的服务
 */
public class FileService {
    public static void sendFileToPersonal(String receiver, String sender, String fileURL, LocalDateTime sendTime) {
        String[] fileName = fileURL.split("\\\\");
        File file = new File(fileURL);
        byte[] bytes = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            Message message = new Message();
            message.setMessageType(MessageType.MESSAGE_TO_PERSONAL_FILE);
            message.setReceiver(receiver);
            message.setSender(sender);
            message.setFileBytes(bytes);
            message.setFileName(fileName[fileName.length - 1]);
            message.setFileLen(file.length());
            message.setSendTime(sendTime);
            ClientConnectServerThread thread =
                    ManageConnectionThreads.getClientConnectServerThread(sender);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(thread.getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("发送成功！");
    }
}
