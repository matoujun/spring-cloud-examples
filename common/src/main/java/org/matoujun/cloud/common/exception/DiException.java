package org.matoujun.cloud.common.exception;

/**
 * @author matoujun

 */
public class DiException extends RuntimeException {
    public DiException(String msg) {
        super(msg);
    }

    public DiException(String msg, Throwable e) {
        super(msg, e);
    }
}
