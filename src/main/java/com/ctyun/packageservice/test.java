package com.ctyun.packageservice;


import java.io.IOException;

public class test {
    public static void main(String[] args) {
        RemoteShellExecutor se = new RemoteShellExecutor("192.168.10.181", "root", "123456");
        boolean login = false;
        try {
            login = se.login();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int pwd = 0;
        try {
            pwd = se.exec("sh /luban_package/test.sh >> /logs/test.log 2>&1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(login);
        System.out.println(pwd);
    }
}
