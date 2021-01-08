package org.example.uploader;

import com.jcraft.jsch.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Base64;

class SftpTest {

    @Test
    void uploadFileUsingJsch() throws JSchException, SftpException {
        var channelSftp = setupJsch();
        channelSftp.connect();

        var localFile = "src/main/resources/fileToUpload.txt";
        channelSftp.put(localFile, String.format("upload/uploadedFile_%s.txt", LocalDateTime.now()));

        channelSftp.exit();
    }

    private ChannelSftp setupJsch() throws JSchException {
        var atmozHostKey = Base64.getDecoder().decode("AAAAB3NzaC1yc2EAAAADAQABAAACAQDnL8cc3eT298LV8S2bJBTyHoqgdMwW57VmNoHAkzDCv88UOg3+rw6hg7b/7a15pVjHK30qsXXVpmxhf6kJoahO4BR4+8JnNG/1050Buc7hba8atrdhs9qd4gfwJ69rpuXU45fZE6zNhiiqVwE4HnWHxz77EWotQsV/wOO4guy9fzBnR2xiskpwT77hcHdNwhuCJuX2ZuFICvz+BQnr7JWO1uBUQNdCGi086Bt294G0bModzUvw9hgshAV4wTmqwOyD613fmKNBdYN5di77H5x1Glxv480Relb1VL64V5gjsVPhyPC44WWQPKk2+bupxZldDLy1Ge/192P2gU21Vf8vT9N7N7JaYO9Lsrw2jbozBhhBR8BUkpfd5TcXbCL9EBWT2I9jCbnKopZ+1cX5aJeoza3/Ueiz6j5UovBi+4y25bzYbs03Jbdl2XXKJ6wZrBYcqU9h6+7SMtC3ZSaNQk81MtjtQl3dwswWXqAuFMdL8tAQ2c4i6yhfwWfgM5YkYQHBxrej8+sQP/lIilxUJR0Q239emjho5+ddmKpMvhjXDjg47EVjPxESj6MaMj05PUKs/ucAem4mqnvHcUorDg/HySiZg2u7C7qiiSpOHwDHTfXf6nbfXQgX8SNj1UMKMEoJDEcl2QEjtuRO/dQ7y+s0bBSYPh8vSfjyKDeEKOy9iQ==");
        var atmozHost = "127.0.0.1";
        var atmozPort = 2222;
        var clientPrivateKeyPathFile = "id_rsa";
        var user = "foo";

        var jsch = new JSch();
        var hostKey = new HostKey(atmozHost, atmozHostKey);
        jsch.addIdentity(clientPrivateKeyPathFile);
        jsch.getHostKeyRepository().add(hostKey, null);

        var jschSession = jsch.getSession(user, atmozHost, atmozPort);
        jschSession.connect();

        System.out.println("Session connected:" + jschSession.isConnected());
        return (ChannelSftp) jschSession.openChannel("sftp");
    }
}