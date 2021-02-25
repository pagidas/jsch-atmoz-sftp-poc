package org.example.uploader.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import io.micronaut.context.annotation.Value;
import lombok.SneakyThrows;

import javax.inject.Singleton;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Singleton
public class UploaderService {

    private final ChannelSftp channelSftp;
    private final String uploadFolder;

    public UploaderService(ChannelSftp channelSftp, @Value("${sftp.upload-folder}") String uploadFolder) {
        this.channelSftp = channelSftp;
        this.uploadFolder = uploadFolder;
    }

    @SneakyThrows(SftpException.class)
    public void uploadContent(String content) {
        var inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        channelSftp.put(inputStream, String.format("%s/uploadedFile_%s.txt", uploadFolder, LocalDateTime.now()));
        channelSftp.exit();
    }
}
