package com.wechat.service;

import com.wechat.common.MessageType;
import com.wechat.common.User;
import com.wechat.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 用于验证用户登录信息和用户注册等功能
 */
public class UserService {
    
    /**
     * 用户对象
     */
    private static final User user = new User();
    /**
     * 调用此服务的User对象与服务器保持通信的socket
     */
    private static Socket socket;
    /**
     * 默认保存文件的位置
     */
    public static String defaultFileDirectory = "C:\\wechat\\FileStorage\\";
    
    /**
     * 验证用户登录信息
     *
     * @param userID   用户ID
     * @param password 密码
     * @return 是否验证通过
     */
    public static boolean checkUser(String userID, String password) {
        
        boolean b = false;
        
        // 设置用户的ID和密码
        user.setUserID(userID);
        user.setPassword(password);
        
        try {
            // 连接服务器
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            
            //发送user对象用于服务器验证
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(user);
            
            // 接收服务器验证信息
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) objectInputStream.readObject();
            // 判断验证状态，进行相应操作
            switch (message.getMessageType()) {
                // 登录成功，创建一个线程和服务器保持通信
                case MessageType.MESSAGE_LOGIN_SUCCESS:
                    ClientConnectServerThread socketThread = new ClientConnectServerThread(socket);
                    socketThread.start();
                    
                    // 将线程加入到管理集合中
                    ManageConnectionThreads.addClientConnectServerThread(userID, socketThread);
                    
                    b = true;
                    System.out.println("=============欢迎用户 [" + userID + "]=============");
                    break;
                // 已经在线，将连接关闭
                case MessageType.MESSAGE_USER_ONLINE:
                    System.out.println("===========用户 [" + userID + "]已经在线===========");
                    socket.close();
                    break;
                // 登录失败，将连接关闭
                case MessageType.MESSAGE_LOGIN_FAIL:
                    System.out.println("===========登陆失败===========");
                    socket.close();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    
    /**
     * 拉取在线用户列表
     */
    public static void getOnlineFriends() {
        // 发送一个Message，类型 MESSAGE_GET_ONLINE_FRIEND
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getUserID());
        message.setSendTime(LocalDateTime.now());
        try {
            // 获取当前线程的socket对应的对象输出流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // 将Message对象发送给服务器
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 退出客户端，给服务器发送退出系统的Message对象
     */
    public static void  signOut() {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserID());
        message.setSendTime(LocalDateTime.now());
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            System.out.println("\n============您（用户 " + user.getUserID() + " ）已退出系统============");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
