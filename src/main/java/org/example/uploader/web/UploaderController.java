package org.example.uploader.web;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;

import javax.validation.constraints.NotNull;

@Controller
@Validated
public class UploaderController {

    @Post(value = "/upload", processes = MediaType.TEXT_PLAIN)
    HttpResponse<String> upload(@NotNull @Body String content) {
        var filename = "filename_123";

        return HttpResponse
                .status(HttpStatus.OK)
                .body(String.format("File %s has been uploaded to atmoz sftp server!", filename));
    }

}
