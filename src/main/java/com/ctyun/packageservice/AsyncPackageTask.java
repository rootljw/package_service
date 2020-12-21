package com.ctyun.packageservice;

import com.ctyun.packageservice.controller.StartPackageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Future;

@Component
public class AsyncPackageTask {
    private static final Logger logger = LoggerFactory.getLogger(StartPackageController.class);
    private RemoteShellExecutor se = new RemoteShellExecutor("192.168.10.181", "root", "123456");
    public int commandResult = -1;

    @Async
    public void packageAll() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("开始打包all");
        boolean login = false;
        try {
            login = se.login();
            logger.info("登录状态:" + login);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String commd = "sh /luban_package/package/dss.pack.sh >> /logs/package.log 2>&1 && sh /luban_package/package/datav.pack.sh  >> /logs/package.log 2>&1 && sh /luban_package/package/linkis.pack.sh >> /logs/package.log 2>&1 ";
            commandResult = se.exec(commd);
            logger.info("执行结果回执:" + commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long currentTimeMillis1 = System.currentTimeMillis();
        logger.info("packageDss耗时" + (currentTimeMillis1 - currentTimeMillis) + "ms");
    }
    @Async
    public void packageDss() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("开始打包dss");
        boolean login = false;
        try {
            login = se.login();
            logger.info("登录状态:" + login);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String commd = "sh /luban_package/package/dss.pack.sh >> /logs/package.log 2>&1";
            commandResult = se.exec(commd);
            logger.info("执行结果回执:" + commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long currentTimeMillis1 = System.currentTimeMillis();
        logger.info("packageDss耗时" + (currentTimeMillis1 - currentTimeMillis) + "ms");
    }
    @Async
    public void packageLinkis() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("开始打包linkis");
        boolean login = false;
        try {
            login = se.login();
            logger.info("登录状态:" + login);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String commd = "sh /luban_package/package/linkis.pack.sh >> /logs/package.log 2>&1";
            commandResult = se.exec(commd);
            logger.info("执行结果回执:" + commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long currentTimeMillis1 = System.currentTimeMillis();
        logger.info("packageLinkis耗时" + (currentTimeMillis1 - currentTimeMillis) + "ms");
    }
    @Async
    public void packageDatav() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("开始打包datav");
        boolean login = false;
        try {
            login = se.login();
            logger.info("登录状态:" + login);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String commd = "sh /luban_package/package/datav.pack.sh >> /logs/package.log 2>&1";
            commandResult = se.exec(commd);
            logger.info("执行结果回执:" + commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long currentTimeMillis1 = System.currentTimeMillis();
        logger.info("packageDatav耗时" + (currentTimeMillis1 - currentTimeMillis) + "ms");
    }
    @Async
    public void stopAll() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("停止所有打包任务");
        boolean login = false;
        try {
            login = se.login();
            logger.info("登录状态:" + login);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String commd = "ps -aux | grep luban_package | grep -v grep | awk '{print $2}' | xargs kill -9 ";
            commandResult = se.exec(commd);
            logger.info("执行结果回执:" + commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long currentTimeMillis1 = System.currentTimeMillis();
        logger.info("停止打包任务" + (currentTimeMillis1 - currentTimeMillis) + "ms");
    }
    @Async
    public void makeServiceAvilable() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("使打包服务可用");
        boolean login = false;
        try {
            login = se.login();
            logger.info("登录状态:" + login);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String commd = "echo \"success\" >> /logs/package.log";
            commandResult = se.exec(commd);
            logger.info("执行结果回执:" + commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long currentTimeMillis1 = System.currentTimeMillis();
        logger.info("使打包服务可用耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
    }




    @Async
    public void task01() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("开始测试");
        boolean login = false;
        try {
            login = se.login();
            logger.info("登录状态:" + login);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String commd = "sh /luban_package/test.sh >> /logs/package.log 2>&1";
            commandResult = se.exec(commd);
            logger.info("执行结果回执:" + commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long currentTimeMillis1 = System.currentTimeMillis();
        logger.info("task01耗时" + (currentTimeMillis1 - currentTimeMillis) + "ms");

    }
}



