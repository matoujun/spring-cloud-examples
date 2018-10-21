package org.matoujun.cloud.common.http;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author matoujun

 */
@Data
public class ErrorDetails implements Serializable{

    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
