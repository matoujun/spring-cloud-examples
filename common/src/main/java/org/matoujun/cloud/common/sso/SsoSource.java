package org.matoujun.cloud.common.sso;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author matoujun

 */
@ConfigurationProperties("app.sso")
@Data
@Component
public class SsoSource {
    private String loginUrl;
    private String logoutUrl;
    private String checkCodeUrl;
    private String appId;
    private String appKey;
    private String jumpTo;

    private final Cookie cookie = new Cookie();

    @Data
    public static class Cookie{
        private String domain;
        private int age;
        private String name;
    }


    private Map<String, DomainInfo> mpDomain = new LinkedHashMap<>();

    private Map<Long, DomainInfo> appDomain = new HashMap<>();

    private DomainInfo domainInfo ;

    private static final Logger log = LoggerFactory.getLogger(SsoSource.class);

    @PostConstruct
    public void initialDomains() {
        DomainInfo domainInfoEdu = new DomainInfo();
        domainInfoEdu.setAppId(getAppId());
        domainInfoEdu.setAppKey(getAppKey());
        domainInfoEdu.setJumpTo(getJumpTo());
        domainInfoEdu.setCookieAge(getCookie().getAge());
        domainInfoEdu.setCookieDomain(getCookie().getDomain());
        domainInfo = domainInfoEdu;

        mpDomain.put(getCookie().getDomain(), domainInfoEdu);
        appDomain.put(Long.parseLong(getAppId()), domainInfoEdu);
        setMpDomain(mpDomain);
    }
}
