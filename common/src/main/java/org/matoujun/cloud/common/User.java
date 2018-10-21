package org.matoujun.cloud.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author matoujun

 */
@Data
public class User implements Serializable {
  private String username;
  private String mobile;
  private String mail;
  private String ticket;
}
