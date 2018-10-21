package org.matoujun.cloud.common.sso;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author matoujun

 */
@Getter
@Component
public class SsoParas {
  @Autowired
  private SsoSource ssoSource;
  public String getLogoutUrl() {
    return ssoSource.getLogoutUrl();
  }

  public DomainInfo getCurDomainInfo(HttpServletRequest request) {
    return ssoSource.getDomainInfo();
  }
}
