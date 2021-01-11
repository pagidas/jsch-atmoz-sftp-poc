package org.example.uploader.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import io.micronaut.context.annotation.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Factory
public class SftpChannelSessionFactory {

    private final Logger log = LoggerFactory.getLogger(SftpChannelSessionFactory.class);
    private final SftpConfiguration sftpConfig;

    public SftpChannelSessionFactory(SftpConfiguration sftpConfig) {
        this.sftpConfig = sftpConfig;
    }

    @Singleton
    public ChannelSftp jschSftpSession() {
        var decodedKey = Base64.getDecoder().decode(sftpConfig.getServer().getPublicKey());

        var jsch = new JSch();
        try {
            // creates sftp server's host key of the sftp server
            var hostKey = new HostKey(
                    sftpConfig.getServer().getHost(),
                    decodedKey);

            // adds sftp server's host key into jsch
            jsch.getHostKeyRepository().add(hostKey, null);

            // adds client's private and public key for jsch to authorize identity before connecting
            jsch.addIdentity(
                    "clientName",
                    sftpConfig.getClient().getPrivateKey().getBytes(StandardCharsets.UTF_8),
                    sftpConfig.getClient().getPublicKey().getBytes(StandardCharsets.UTF_8),
                    null);

            // creates jsch session with client's user, and sftp server's host and port
            var jschSession = jsch.getSession(
                    sftpConfig.getClient().getUser(),
                    sftpConfig.getServer().getHost(),
                    sftpConfig.getServer().getPort());
            // it won't complain because we use public-private-key authentication
            jschSession.setPassword("no-password-at-the-moment");
            jschSession.connect();

            log.info("Session connected {}", jschSession.isConnected());
            return (ChannelSftp) jschSession.openChannel("sftp");
        } catch (JSchException e) {
            log.error("Jsch failed with message: {}", e.getMessage());
        }
        return null;
    }
}
