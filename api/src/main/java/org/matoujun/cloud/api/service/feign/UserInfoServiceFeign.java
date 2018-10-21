package org.matoujun.cloud.api.service.feign;

import org.matoujun.cloud.common.User;
import org.matoujun.cloud.common.http.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: matoujun
 */
@FeignClient("uaa")
public interface UserInfoServiceFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/user/get")
    RestResponse<User> getCurrentUser();
}
