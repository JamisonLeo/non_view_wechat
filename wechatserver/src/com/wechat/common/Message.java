package com.wechat.common;

import java.io.Serializable;

/**
 * 客户端和服务器端通讯时的消息对象
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 接收者
     */
    private String receiver;
    /**
     * 发送内容
     */
    private String content;
    /**
     * 发送时间
     */
    private String sendTime;
    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件的字节数组
     */
    private byte[] fileBytes;
    /**
     * 文件的长度
     */
    private long fileLen;
    
    /**
     * @return 发送者ID
     */
    public String getSender() {
        return sender;
    }
    
    /**
     * 设置发送者
     * @param sender 发送者ID
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    /**
     * @return 接收者ID
     */
    public String getReceiver() {
        return receiver;
    }
    
    /**
     * 设置接收者
     * @param receiver 接收者ID
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    /**
     * @return 消息内容
     */
    public String getContent() {
        return content;
    }
    
    /**
     * 存放消息内容
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * @return 发送时间
     */
    public String getSendTime() {
        return sendTime;
    }
    
    /**
     * 设置发送时间
     * @param sendTime 发送时间
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    
    /**
     * @return 消息类型
     */
    public String getMessageType() {
        return messageType;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public byte[] getFileBytes() {
        return fileBytes;
    }
    
    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
    
    public int getFileLen() {
        return (int) fileLen;
    }
    
    public void setFileLen(long fileLen) {
        this.fileLen = fileLen;
    }
    
    /**
     * 注明消息类型
     * @param messageType 消息类型
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
