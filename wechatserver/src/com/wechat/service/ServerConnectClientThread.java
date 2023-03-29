package com.wechat.service;

import com.wechat.common.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 服务器与客户端连接的线程类
 */
public class ServerConnectClientThread extends Thread {
    private final Socket socket;
    private final String userID;
    
    public ServerConnectClientThread(Socket socket, String userID) {
        this.socket = socket;
        this.userID = userID;
    }
    
    @Override
    public void run() {
        boolean b = true;
        while (b) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                switch (message.getMessageType()) {
                    /*拉取在线用户*/
                    case MessageType.MESSAGE_GET_ONLINE_FRIEND:
                        System.out.println(message.getSendTime() + " | [" + userID + "] 拉取在线用户列表……");
                        String onlineUsers = ManageConnectionThreads.getOnlineUsers();
                        // 新建一个Message对象，返回给客户端
                        Message returnMessage = new Message();
                        returnMessage.setMessageType(MessageType.MESSAGE_RETURN_ONLINE_FRIEND);
                        returnMessage.setContent(onlineUsers);
                        returnMessage.setReceiver(userID);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(returnMessage);
                        break;
                    /*客户端退出，结束对应的线程*/
                    case MessageType.MESSAGE_CLIENT_EXIT:
                        System.out.println(message.getSendTime() + " | 用户[" + userID + "]退出系统");
                        ManageConnectionThreads.deleteServerConnectClientThread(userID);
                        socket.close();
                        b = false;
                        break;
                    /*客户端之间私发消息、文件*/
                    case MessageType.MESSAGE_TO_PERSONAL_MESSAGE:
                    case MessageType.MESSAGE_TO_PERSONAL_FILE:
                        ServerConnectClientThread receiverThread =
                                ManageConnectionThreads.getServerConnectClientThread(message.getReceiver());
                        ObjectOutputStream objectOutputStream1 =
                                new ObjectOutputStream(receiverThread.getSocket().getOutputStream());
                        objectOutputStream1.writeObject(message);
                        break;
                    /*群发消息*/
                    case MessageType.MESSAGE_TO_EVERYONE_MESSAGE:
                        for (ServerConnectClientThread thread : ManageConnectionThreads.getHashMap().values()) {
                            // 如果遍历到的线程是发送消息的线程则略过
                            if (thread.getUserID().equals(message.getSender())) {
                                continue;
                            }
                            ObjectOutputStream objectOutputStream2 =
                                    new ObjectOutputStream(thread.getSocket().getOutputStream());
                            objectOutputStream2.writeObject(message);
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @return 当前通信线程的socket
     */
    public Socket getSocket() {
        return socket;
    }
    
    /**
     * @return 当前线程的userID
     */
    public String getUserID() {
        return userID;
    }
}
