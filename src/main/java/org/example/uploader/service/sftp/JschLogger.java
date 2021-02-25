package org.example.uploader.service.sftp;

import org.slf4j.Logger;

/**
 * This is a custom way to let Jsch display its logs
 */
public class JschLogger implements com.jcraft.jsch.Logger {

    private final Logger log;

    /**
     * @param log - the top-level slf4j logging framework
     */
    public JschLogger(Logger log) {
        this.log = log;
    }

    /*
    No mapping between our levels and Jsch levels. The logger is just enabled
     */
    @Override
    public boolean isEnabled(int level) {
        return true;
    }

    /*
    It uses just the top-level sfl4j logger on DEBUG.
     */
    @Override
    public void log(int level, String message) {
        log.debug(message);
    }
}
