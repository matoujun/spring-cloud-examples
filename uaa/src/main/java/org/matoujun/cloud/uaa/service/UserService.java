package org.matoujun.cloud.uaa.service;

import org.matoujun.cloud.common.User;

import javax.servlet.http.HttpServletRequest;


/**
 * @author matoujun
 */
public interface UserService {


    String getCurrentUserName(HttpServletRequest request);

    User getCurrentUser(String ticket);

    User getCurrentUser(HttpServletRequest request);
}
