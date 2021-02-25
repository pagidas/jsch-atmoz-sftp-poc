package org.example.uploader.service.sftp;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("sftp")
public class SftpConfiguration {

    private ClientConfig client;
    private ServerConfig server;

    @Data
    @ConfigurationProperties("client")
    public static class ClientConfig {
        private String user;
        private String publicKey;
        private String privateKey;
    }

    @Data
    @ConfigurationProperties("server")
    public static class ServerConfig {
        private String host;
        private Integer port;
        private String publicKey;
    }
}
