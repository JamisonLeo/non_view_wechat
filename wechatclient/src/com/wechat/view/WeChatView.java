package com.wechat.view;

import com.wechat.service.FileService;
import com.wechat.service.MessageService;
import com.wechat.service.UserService;
import com.wechat.utils.Utility;

import java.time.LocalDateTime;

/**
 * 客户端的菜单界面
 */
public class WeChatView {
    
    // 控制是否显示菜单
    private boolean loop = true;
    // 接收用户的键盘输入
    private String key;
    
    public static void main(String[] args) {
        new WeChatView().mainView();
    }
    
    /**
     * 显示客户端菜单界面
     */
    private void mainView() {
        while (loop) {
            System.out.println("==============欢迎登陆WeChat==============");
            System.out.println("\t\t1 登录系统");
            System.out.println("\t\t9 退出系统");
            System.out.print("\n请输入你的选择：");
            
            key = Utility.readString(1);
            
            switch (key) {
                case "1":
                    System.out.print("请输入账号：");
                    String userID = Utility.readString(11);
                    System.out.print("请输入密码：");
                    String password = Utility.readString(18);
                    // 到服务器验证用户是否存在
                    if (UserService.checkUser(userID, password)) {
                        // 进入二级菜单
                        while (loop) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("\n============WeChat二级菜单(用户[" + userID + "])=============");
                            System.out.println("\t\t1 显示在线用户列表");
                            System.out.println("\t\t2 群发消息");
                            System.out.println("\t\t3 私聊消息");
                            System.out.println("\t\t4 发送文件");
                            System.out.println("\t\t9 退出系统");
                            System.out.print("\n请输入你的选择：");
    
                            key = Utility.readString(1);
                            
                            switch (key) {
                                /*显示在线用户列表*/
                                case "1":
                                    UserService.getOnlineFriends();
                                    break;
                                /*群发消息*/
                                case "2":
                                    System.out.print("发给所有人：");
                                    String s = Utility.readString(1000);
                                    MessageService.sendMessageToEveryone(userID, s,LocalDateTime.now());
                                    break;
                                /*私聊信息*/
                                case "3":
                                    System.out.print("\n给谁发送消息(输入用户名)：");
                                    String receiver = Utility.readString(20);
                                    System.out.print("\n发给" + receiver + "：");
                                    String sendMessage = Utility.readString(1000);
                                    MessageService.sendMessageToPersonal(receiver, userID, sendMessage, LocalDateTime.now());
                                    break;
                                /*发送文件*/
                                case "4":
                                    System.out.print("\n给谁发送文件(输入用户名)：");
                                    String receiver1 = Utility.readString(20);
                                    System.out.print("\n输入要发送的文件的路径：");
                                    String filePath = Utility.readString(1000);
                                    FileService.sendFileToPersonal(receiver1, userID, filePath, LocalDateTime.now());
                                    System.out.println("发送文件");
                                    break;
                                /*退出系统*/
                                case "9":
                                    loop = false;
                                    UserService.signOut();
                                    break;
                            }
                        }
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
