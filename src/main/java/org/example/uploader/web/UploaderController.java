package org.example.uploader.web;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.example.uploader.service.UploaderService;

import javax.validation.constraints.NotNull;

@Controller
public class UploaderController {

    private final UploaderService uploaderService;

    public UploaderController(UploaderService uploaderService) {
        this.uploaderService = uploaderService;
    }

    @Post(value = "/upload", processes = MediaType.TEXT_PLAIN)
    HttpResponse<String> upload(@NotNull @Body String content) {
        try {
            uploaderService.uploadContent(content);
        } catch (RuntimeException e) {
            return HttpResponse.serverError("Uploading Failed! -- " + e.getMessage());
        }
        return HttpResponse.ok("File has been uploaded to atmoz sftp server! Take a look in upload/ at the root of repo");
    }
}
