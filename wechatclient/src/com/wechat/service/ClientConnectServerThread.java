package com.wechat.service;

import com.wechat.common.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 客户端连接到服务器的线程类
 */
public class ClientConnectServerThread extends Thread {
    private Socket socket;
    
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 因为需要在后台和服务器保持通信，所以使用无限循环
        while (true) {
            try {
                // 等待读取服务器端发送的消息，服务器不发送消息则阻塞在这里
                Message message = (Message) objectInputStream.readObject();
                switch (message.getMessageType()) {
                    /*拉取用户列表*/
                    case MessageType.MESSAGE_RETURN_ONLINE_FRIEND:
                        String[] onlineUsersList = message.getContent().split(" ");
                        System.out.println("===========当前在线用户列表===========");
                        for (String s : onlineUsersList) {
                            System.out.println("\t\t用户  " + s);
                        }
                        break;
                    /*显示私聊信息*/
                    case MessageType.MESSAGE_TO_PERSONAL_MESSAGE:
                        System.out.println();
                        System.out.println(message.getSendTime() + " | "
                                + message.getSender() + "：" + message.getContent());
                        break;
                    /*显示群发消息*/
                    case MessageType.MESSAGE_TO_EVERYONE_MESSAGE:
                        System.out.println();
                        System.out.println(message.getSendTime() + " | "
                                + message.getSender() + "：[所有人] " + message.getContent());
                        break;
                    /*显示私发文件*/
                    case MessageType.MESSAGE_TO_PERSONAL_FILE:
                        File defaultDirectory =
                                new File(UserService.defaultFileDirectory + message.getReceiver());
                        if (!defaultDirectory.exists()) {
                            defaultDirectory.mkdirs();
                        }
                        FileOutputStream fileOutputStream =
                                new FileOutputStream(new File(UserService.defaultFileDirectory +
                                        message.getReceiver() + "\\" + message.getFileName()));
                        fileOutputStream.write(message.getFileBytes());
                        System.out.println();
                        System.out.println(message.getSendTime() + " | "
                                + message.getSender() + "：[" + message.getFileName() + "] 已保存到"
                                + UserService.defaultFileDirectory + message.getReceiver() + "\\"
                                + message.getFileName());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public Socket getSocket() {
        return socket;
    }
}
