package org.matoujun.cloud.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.matoujun.cloud.common.User;
import org.matoujun.cloud.api.service.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
/**
 * User: matoujun
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
  @Value("${app.sso.cookie.name}")
  private String userCookName;

  @Override
  public User currentUser(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, userCookName);
    String ticket = "";
    if (null != cookie) {
      ticket = cookie.getValue();
    }
    User user = new User();
    user.setMail("cloud@matoujun.org");
    return user;
  }

}
