package com.ctyun.packageservice;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.ctyun.packageservice.controller.StartPackageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class test02 {
    private static final Logger logger = LoggerFactory.getLogger(StartPackageController.class);
    public static void main(String[] args) throws IOException {


//        sftpDownload("")

    }

    public static Connection getConnect(String user,String password ,String ip,int port ) {
        Connection conn=new Connection(ip, port);
        try {

            conn.connect();
            conn.authenticateWithPassword(user, password);
            logger.info("ssh连接成功");

        }catch(Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static boolean sftpDownload(String remoteFilePath,String localFilePath,String user,String password ,String ip,int port) {
        boolean bool=false;
        Connection connection=null;
        try {
            connection=getConnect(user, password, ip, port);
            SCPClient scpClient = connection.createSCPClient();
            scpClient.put(localFilePath, remoteFilePath);
            bool=true;
        }catch(IOException ioe) {
            ioe.printStackTrace();
            bool =false;
        }finally {
            connection.close();
        }
        return bool;
    }
}
