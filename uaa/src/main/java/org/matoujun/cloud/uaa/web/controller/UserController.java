package org.matoujun.cloud.uaa.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.matoujun.cloud.common.User;
import org.matoujun.cloud.common.http.RestResponse;
import org.matoujun.cloud.common.sso.DomainInfo;
import org.matoujun.cloud.common.sso.SsoParas;
import org.matoujun.cloud.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author matoujun
 */
@Slf4j
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private SsoParas ssoSwitch;

  @GetMapping("/login")
  public RestResponse<Map<String, Object>> userGet(HttpServletRequest request) {
    RestResponse<Map<String, Object>> restResponse = new RestResponse<>();
    String requestDomain = request.getHeader("x-forwarded-host");
    log.info("requestDomain:" + requestDomain);
    User user = userService.getCurrentUser(request);
    restResponse.setErrno(1);
    restResponse.setErrmsg("OK");
    Map<String, Object> baneResponseData = new HashMap<>();
    baneResponseData.put("email", user.getMail());
    baneResponseData.put("name", user.getUsername());
    restResponse.setData(baneResponseData);
    return restResponse;
  }

  @GetMapping("/logout")
  public RestResponse userLogout(HttpServletRequest request, HttpServletResponse response) {
    RestResponse<Map<String, String>> restResponse = new RestResponse<>();
    HttpSession session = request.getSession();
    session.invalidate();
    String logoutURL = ssoSwitch.getLogoutUrl();
    DomainInfo domainInfo = ssoSwitch.getCurDomainInfo(request);
    String appid = domainInfo.getAppId();
    String jumpto = domainInfo.getJumpTo();
    restResponse.setErrno(1);
    restResponse.setErrmsg("OK");
    Map<String, String> diResponseData = new HashMap<>();
    diResponseData.put("logoutURL", logoutURL + "?app_id=" + appid + "&jumpto=" + jumpto);
    restResponse.setData(diResponseData);
    return restResponse;
  }
}
