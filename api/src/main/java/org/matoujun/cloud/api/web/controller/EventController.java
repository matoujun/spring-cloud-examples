package org.matoujun.cloud.api.web.controller;

import org.matoujun.cloud.common.http.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * @author matoujun
 */
@RestController
@RequestMapping(value = "/api/event", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class EventController extends BaseController {

    @GetMapping("/yummy/get")
    public RestResponse getCase(@RequestParam(name = "id") Long id) {

        String caseInfo = "caseInfo";
        RestResponse<String> restResponse = new RestResponse<>();
        restResponse.setData(caseInfo);
        restResponse.setErrno(1);
        restResponse.setErrmsg("OK");

        return restResponse;
    }
}
