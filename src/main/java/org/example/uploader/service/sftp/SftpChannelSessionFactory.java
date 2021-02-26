package org.example.uploader.service.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import io.micronaut.context.annotation.Factory;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Factory
public class SftpChannelSessionFactory {

    private final String SFTP = "sftp";
    private final SftpConfiguration sftpConfig;

    public SftpChannelSessionFactory(SftpConfiguration sftpConfig) {
        this.sftpConfig = sftpConfig;
    }

    @Singleton
    public ChannelSftp jschSftpSession() {
        JSch.setLogger(new JschLogger(log));

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
            jschSession.setPassword(sftpConfig.getClient().getPassword());

            /*
            Notes on configuring the client.

            - Can skip hostKeyAuthentication if we don't know the server's public key.
            - Can choose our PreferredAuthentication methods order.

            The order of PreferredAuthentication methods might be put from the server,
            example, 2FA, or both public key and password. So we might not be able to pick up our order.
             */
//            jschSession.setConfig("StrictHostKeyChecking", "no");
//            jschSession.setConfig("PreferredAuthentications", "publickey,password,keyboard-interactive");

            jschSession.connect();
            log.info("Session connected {}", jschSession.isConnected());

            var sftpChannel = (ChannelSftp) jschSession.openChannel(SFTP);
            sftpChannel.connect();
            log.info("SFT channel created {}", sftpChannel.isConnected());

            return sftpChannel;
        } catch (JSchException e) {
            log.error("Jsch failed with message: {}", e.getMessage());
        }
        return null;
    }
}
