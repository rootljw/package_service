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

public class RemoteShellExecutor {


    private Connection conn;
    private String ip;
    private String username;
    private String password;
    private static final int TIME_OUT = 0;
    private static final Logger logger = LoggerFactory.getLogger(StartPackageController.class);


    public RemoteShellExecutor(String ip, String username, String password) {
        this.ip = ip;
        this.username = username;
        this.password = password;
    }


    public boolean login() throws IOException {

        conn = new Connection(ip);
        conn.connect();
        return conn.authenticateWithPassword(username, password);
    }

    public String processStream(InputStream in, String charset) throws IOException {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }

    public int exec(String shell) throws Exception {
        /**
         * @Description
         * @Author ljw
         * @Date 2020/11/26 13:59
         * @Param [shell]
         * @Return int
         * @Exception
         *
         */

        int ret = -1;
        try {
            if (login()) {
                Session session = conn.openSession();
                session.execCommand(shell);
                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
                ret = session.getExitStatus();
            } else {
                throw new Exception("登录远程机器失败" + ip);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return ret;
    }


    private String charset = Charset.defaultCharset().toString();

    public Boolean isPackaging() throws IOException {
        String outStr = "";
        InputStream stdOut = null;

        int ret = -1;
        try {
            if (login()) {
                Session session = conn.openSession();
                session.execCommand("tail -1 /logs/package.log");
                stdOut = new StreamGobbler(session.getStdout());
                outStr = processStream(stdOut, charset);
                logger.info("打包日志最后一行的内容为: "+outStr.trim());
                logger.info("是否正在打包?: " + !"success".equals(outStr.trim()));
                return !"success".equals(outStr.trim());
            } else {
                return "success".equals(outStr.trim());
            }
        } finally {
            if (conn != null) {
                conn.close();

            }
        }

    }
}
