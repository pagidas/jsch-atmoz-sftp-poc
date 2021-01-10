package org.example.uploader.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.commons.io.FileUtils;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Singleton
public class UploaderService {

    private final ChannelSftp channelSftp;

    public UploaderService(ChannelSftp channelSftp) {
        this.channelSftp = channelSftp;
    }

    public void uploadContent(String content) throws SftpException, IOException, JSchException {
        var fileToUpload = new File("fileToUpload");
        FileUtils.writeStringToFile(fileToUpload, content, StandardCharsets.UTF_8);

        channelSftp.connect();
        channelSftp.put("fileToUpload", String.format("upload/uploadedFile_%s.txt", LocalDateTime.now()));
        channelSftp.exit();

        FileUtils.deleteQuietly(fileToUpload);
    }
}
