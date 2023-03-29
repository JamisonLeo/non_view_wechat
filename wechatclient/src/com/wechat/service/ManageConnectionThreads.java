package com.wechat.service;

import java.util.HashMap;

/**
 * 管理客户端连接服务器的线程
 */
public class ManageConnectionThreads {
    // 存放连接服务器的线程，key 是 userID，value 是 该线程
    private static HashMap<String, ClientConnectServerThread> hashMap = new HashMap<>();
    
    /**
     * 将连接到服务器的线程添加到集合当中
     *
     * @param userID           用户ID
     * @param connectionThread 连接到服务器的线程
     */
    public static void addClientConnectServerThread(String userID, ClientConnectServerThread connectionThread) {
        hashMap.put(userID, connectionThread);
    }
    
    /**
     * 根据用户ID返回该用户与服务器建立连接的线程
     *
     * @param userID 用户ID
     * @return 该用户与服务器建立连接的线程
     */
    public static ClientConnectServerThread getClientConnectServerThread(String userID) {
        return hashMap.get(userID);
    }
}
