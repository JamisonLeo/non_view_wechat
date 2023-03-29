package com.wechat.service;

import java.util.HashMap;

/**
 * 管理和客户端通信的线程
 */
public class ManageConnectionThreads {
    private static HashMap<String, ServerConnectClientThread> hashMap = new HashMap<>();
    
    /**
     * 将与客户端保持通信的线程添加到集合中
     *
     * @param userID           用户ID
     * @param connectionThread 与客户端保持通信的线程
     */
    public static void addServerConnectClientThread(String userID, ServerConnectClientThread connectionThread) {
        hashMap.put(userID, connectionThread);
    }
    
    /**
     * 将与客户端保持通信的线程从集合中删除
     * @param userID 用户ID
     */
    public static void deleteServerConnectClientThread(String userID) {
        hashMap.remove(userID);
    }
    
    /**
     * 根据用户ID返回与该客户保持通信的线程
     *
     * @param userID 用户ID
     * @return 与该客户端保持通信的线程
     */
    public static ServerConnectClientThread getServerConnectClientThread(String userID) {
        return hashMap.get(userID);
    }
    
    /**
     * 获取在线用户
     * @return 在线用户的用户ID
     */
    public static String getOnlineUsers() {
        StringBuilder onlineUsers = new StringBuilder();
        for (String key : hashMap.keySet()) {
            onlineUsers.append(key).append(" ");
        }
        return onlineUsers.toString();
    }
    
    /**
     * 获取所有合法用户信息
     * @return 存放合法用户信息的HashMap集合
     */
    public static HashMap<String, ServerConnectClientThread> getHashMap() {
        return hashMap;
    }
}
