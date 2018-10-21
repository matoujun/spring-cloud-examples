package org.matoujun.cloud.api.service;

import org.matoujun.cloud.common.User;
import javax.servlet.http.HttpServletRequest;

/**

 * User: matoujun
 *
 */
public interface UserInfoService {

  User currentUser(HttpServletRequest request);
}
