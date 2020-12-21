package com.ctyun.packageservice.controller;


import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.ctyun.packageservice.AsyncPackageTask;
import com.ctyun.packageservice.RemoteShellExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

/**
 * @author lvjw
 */
@Controller
public class StartPackageController {
    private RemoteShellExecutor se = new RemoteShellExecutor("192.168.10.181", "root", "123456");
    private String charset = Charset.defaultCharset().toString();

    @Autowired
    private AsyncPackageTask asyncPackageTask;

    @RequestMapping("/")
    public String index() {
        try {
            if (se.isPackaging()) {
                return "loading";
            } else {
                return "index";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping("/test")
    public String doTask() throws InterruptedException {
        asyncPackageTask.task01();
        Thread.sleep(500);
        return "redirect:/packaging";


    }

    @RequestMapping("/stopall")
    public String stopall() {

        try {
            asyncPackageTask.stopAll();
            Thread.sleep(1500);
            asyncPackageTask.makeServiceAvilable();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping("/startallpackage")
    public String startAllPackage() {

        try {
            asyncPackageTask.packageAll();
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/packaging";
    }

    @RequestMapping("/startpackagedss")
    public String startPackageDss() {
        try {
            asyncPackageTask.packageDss();
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/packaging";
    }

    @RequestMapping("/packaging")
    public String isPackgeDone() throws Exception {
        if (se.isPackaging()) {
            return "packaging";
        }
        return "redirect:/download";
    }

    @RequestMapping("/download")
    public String download() throws Exception {
        Thread.sleep(1000);
        return "download";
    }


    @RequestMapping("/startpackagelinkis")
    public String startPackageLinkis() {
        try {
            asyncPackageTask.packageLinkis();
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/packaging";
    }

    @RequestMapping("/startpackagedatav")
    public String startPackageDataV() {
        try {
            asyncPackageTask.packageDatav();
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/packaging";
    }

    /**
     * 跳转实时日志
     */
    @GetMapping("/logging")
    public ModelAndView logging() {
        return new ModelAndView("logging.html");
    }

//    public static Connection getConnect(String user, String password, String ip, int port) {
//        Connection conn = new Connection(ip, port);
//        try {
//
//            conn.connect();
//            conn.authenticateWithPassword(user, password);
//            logger.info("ssh连接ok");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }
//
//    public static boolean sftpDownload(String remoteFilePath, String localFilePath, String user, String password, String ip, int port) {
//        boolean bool = false;
//        Connection connection = null;
//        try {
//            connection = getConnect(user, password, ip, port);
//            SCPClient scpClient = connection.createSCPClient();
//            scpClient.put(localFilePath, remoteFilePath);
//            bool = true;
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//            bool = false;
//        } finally {
//            connection.close();
//        }
//        return bool;
//    }


}
