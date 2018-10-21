package org.matoujun.cloud.common.sso;

import lombok.Data;

/**
 * @author matoujun
 */
@Data
public class DomainInfo {
    private String appId;
    private String appKey;
    private String jumpTo;
    private String version;
    private int cookieAge;
    private String cookieDomain;
}
