package org.example;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.junit.jupiter.api.Test;

import java.util.Properties;

class SftpTest {

    @Test
    void uploadFileUsingJsch() throws JSchException, SftpException {
        var channelSftp = setupJsch();
        channelSftp.connect();

        var localFile = "src/main/resources/fileToUpload.txt";
        channelSftp.put(localFile, "temp.txt");

        channelSftp.exit();
    }

    private ChannelSftp setupJsch() throws JSchException {
        var props = new Properties();
//        props.put("StrictHostKeyChecking", "no");

        var jsch = new JSch();
//        jsch.setKnownHosts(".ssh/known_hosts");

        var jschSession = jsch.getSession("foo", "127.0.0.1", 2222);
        jschSession.setPassword("pass");
        jschSession.setConfig(props);
        jschSession.connect();

        System.out.println("Session connected:" + jschSession.isConnected());
        return (ChannelSftp) jschSession.openChannel("sftp");
    }
}