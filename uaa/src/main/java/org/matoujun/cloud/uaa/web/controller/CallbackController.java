package org.matoujun.cloud.uaa.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author matoujun
 */
@Controller
@SessionAttributes({"userSessionInfo", "user"})
@Slf4j
public class CallbackController {
    @RequestMapping("/welcome")
    public void welcome(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        String jumpTo = request.getParameter("jumpto");

        if (jumpTo != null && !"index".equals(jumpTo)) {
            try {
                response.sendRedirect(jumpTo);
            } catch (Exception e) {
                log.error("error jumpto = " + jumpTo, e);
            }
        }
        response.sendRedirect("/index.html");
    }
}
