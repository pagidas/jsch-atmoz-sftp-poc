package org.example.uploader.service;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("sftp")
public class SftpConfiguration {
    private ClientConfig client;
    private ServerConfig server;

    public ClientConfig getClient() {
        return client;
    }

    public void setClient(ClientConfig client) {
        this.client = client;
    }

    public ServerConfig getServer() {
        return server;
    }

    public void setServer(ServerConfig server) {
        this.server = server;
    }

    @ConfigurationProperties("client")
    public static class ClientConfig {
        private String user;
        private String publicKey;
        private String privateKey;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }

    @ConfigurationProperties("server")
    public static class ServerConfig {
        private String host;
        private Integer port;
        private String publicKey;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }
    }
}
