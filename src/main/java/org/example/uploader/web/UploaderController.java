package org.example.uploader.web;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import org.example.uploader.service.UploaderService;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Controller
@Validated
public class UploaderController {

    private final UploaderService uploaderService;

    public UploaderController(UploaderService uploaderService) {
        this.uploaderService = uploaderService;
    }

    @Post(value = "/upload", processes = MediaType.TEXT_PLAIN)
    HttpResponse<String> upload(@NotNull @Body String content) {

        try {
            uploaderService.uploadContent(content);
        } catch (SftpException | IOException | JSchException e) {
            return HttpResponse
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something happened! -- " + e.getMessage());
        }

        return HttpResponse
                .status(HttpStatus.OK)
                .body("File has been uploaded to atmoz sftp server!");
    }
}
