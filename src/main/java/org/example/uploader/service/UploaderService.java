package org.example.uploader.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Value;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Singleton
public class UploaderService {

    // variable -- can be re-created if there is no session
    private Session jschSession;

    private final String uploadFolder;
    private final BeanContext ctx;
    private final String SFTP = "sftp";

    public UploaderService(Session jschSession, @Value("${sftp.upload-folder}") String uploadFolder, BeanContext beanContext) {
        this.jschSession = jschSession;
        this.uploadFolder = uploadFolder;
        this.ctx = beanContext;
    }

    @SneakyThrows({SftpException.class, JSchException.class})
    public void uploadContent(String content) {
        log.info("Attempt to upload given content to sftp server");

        if (!jschSession.isConnected()) {
            // when packet is lost, we have to create a new session.
            ctx.destroyBean(Session.class);
            jschSession = ctx.getBean(Session.class);
        }

        var sftpChannel = (ChannelSftp) jschSession.openChannel(SFTP);
        sftpChannel.connect();
        log.info("SFTP channel created {}", sftpChannel.isConnected());

        var inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        sftpChannel.put(inputStream, String.format("%s/uploadedFile_%s.txt", uploadFolder, LocalDateTime.now()));

        sftpChannel.exit();
        jschSession.disconnect();
    }
}
