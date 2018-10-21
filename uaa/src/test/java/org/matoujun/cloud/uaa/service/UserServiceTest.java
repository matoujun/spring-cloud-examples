package org.matoujun.cloud.uaa.service;

import org.matoujun.cloud.uaa.UaaMain;
import org.matoujun.cloud.uaa.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**

 * User: matoujun
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaMain.class)
public class UserServiceTest {
  @Autowired
  private UserService userService;

  @Test
  public void simple() {
  }
}
