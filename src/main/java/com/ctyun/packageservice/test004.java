package com.ctyun.packageservice;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.ctyun.packageservice.controller.StartPackageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class test004 {
    private static final Logger logger = LoggerFactory.getLogger(StartPackageController.class);
    private RemoteShellExecutor se = new RemoteShellExecutor("192.168.10.181", "root", "123456");
    private String charset = Charset.defaultCharset().toString();
    private Connection conn;
    private static final int TIME_OUT = 1000 * 5 * 60;

    public static void main(String[] args) throws Exception {
        test004 test004 = new test004();
//        System.out.println(test004.isPackaging());
        System.out.println(test004.exec("tail -1 /logs/linkis_package.log"));
    }



    public int exec(String shell) throws Exception {
        /**
         * @Description
         * @Author  ljw
         * @Date   2020/11/26 13:59
         * @Param  [shell]
         * @Return      int
         * @Exception
         *
         */

        int ret = -1;
        try {
            if (se.login()) {
                Session session = conn.openSession();
                session.execCommand(shell);
                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
                ret = session.getExitStatus();
            } else {
                throw new Exception("登录远程机器失败" );
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return ret;
    }

    public Boolean isPackaging() {
        InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr = "";
        int ret = -1;


        try {

            if (se.login()) {
                Session session = conn.openSession();
                session.execCommand("tail -1 /logs/linkis_package.log");
                stdOut = new StreamGobbler(session.getStdout());
                outStr = se.processStream(stdOut, charset);
                logger.info("[INFO] outStr=" + outStr);

                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
                ret = session.getExitStatus();
                return outStr.equals("success");
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }
}
