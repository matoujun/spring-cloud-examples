package org.matoujun.cloud.api.web.controller;


import org.matoujun.cloud.api.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author matoujun
 */
public class BaseController {
    @Autowired
    protected UserInfoService userInfoService;
}
