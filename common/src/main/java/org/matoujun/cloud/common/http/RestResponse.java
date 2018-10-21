package org.matoujun.cloud.common.http;

import lombok.Data;

import java.io.Serializable;

/**
 * @author matoujun

 */
@Data
public class RestResponse<T> implements Serializable {
  private Integer errno;
  private String errmsg;
  private T data;

  public RestResponse() {
  }

  public RestResponse(Integer errno, String errmsg, T data) {
    this.errno = errno;
    this.errmsg = errmsg;
    this.data = data;
  }


  public static <T> RestResponse<T> success(T data, String message) {
    RestResponse<T> baneResponse = new RestResponse<>();
    baneResponse.setData(data);
    baneResponse.setErrmsg(message);
    baneResponse.setErrno(1);
    return baneResponse;
  }


  public RestResponse ok(T data) {
    this.setErrno(1);
    this.setErrmsg("OK");
    this.setData(data);
    return this;
  }
}
