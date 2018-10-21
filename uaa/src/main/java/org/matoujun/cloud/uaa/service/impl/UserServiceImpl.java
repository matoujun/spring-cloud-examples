package org.matoujun.cloud.uaa.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.matoujun.cloud.common.User;
import org.matoujun.cloud.uaa.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author matoujun
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Override
  public String getCurrentUserName(HttpServletRequest request) {
    return getCurrentUser(request).getUsername();
  }


  @Override
  public User getCurrentUser(String ticket) {
    User user = new User();
    user.setUsername("cloud");
    user.setMail("cloud@matoujun.org");

    return user;
  }

  @Override
  public User getCurrentUser(HttpServletRequest request) {
    User user = new User();
    user.setUsername("cloud");
    user.setMail("cloud@matoujun.org");
    return user;
  }

}
