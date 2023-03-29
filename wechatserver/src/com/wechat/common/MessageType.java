package com.wechat.common;

/**
 * 表示消息类型
 * <p>定义了一些常量，不同的常量的值表示不同的消息类型。</p>
 */
public interface MessageType {
    /**
     * 用户登录成功
     */
    String MESSAGE_LOGIN_SUCCESS = "1";
    /**
     * 用户登录失败
     */
    String MESSAGE_LOGIN_FAIL = "2";
    /**
     * 用户不存在
     */
    String MESSAGE_USER_NO_EXIST = "3";
    /**
     * 用户已经在线
     */
    String MESSAGE_USER_ONLINE = "4";
    /**
     * 私聊消息
     */
    String MESSAGE_TO_PERSONAL_MESSAGE = "5";
    /**
     * 拉取在线用户列表
     */
    String MESSAGE_GET_ONLINE_FRIEND = "6";
    /**
     * 返回的在线用户列表
     */
    String MESSAGE_RETURN_ONLINE_FRIEND = "7";
    /**
     * 客户端退出
     */
    String MESSAGE_CLIENT_EXIT = "8";
    /**
     * 群发消息
     */
    String MESSAGE_TO_EVERYONE_MESSAGE = "9";
    /**
     * 私发文件
     */
    String MESSAGE_TO_PERSONAL_FILE = "10";
}
