package org.example;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JschTest {

    private ChannelSftp channelSftp;

    @BeforeEach
    void setUp() {
        try {
            channelSftp = setupJsch();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    @Test
    void uploadFileUsingJsch() throws JSchException, SftpException {
        channelSftp.connect();

        var localFile = "src/main/resources/fileToUpload.txt";
        channelSftp.put(localFile, "jschFile.txt");

        channelSftp.exit();
    }

    private ChannelSftp setupJsch() throws JSchException {
        var jsch = new JSch();
        jsch.setKnownHosts("./known_hosts");
        var jschSession = jsch.getSession("foo", "localhost", 2222);
        jschSession.setPassword("pass");
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }
}