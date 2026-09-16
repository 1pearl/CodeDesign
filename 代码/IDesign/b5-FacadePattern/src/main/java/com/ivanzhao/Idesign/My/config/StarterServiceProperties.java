package com.ivanzhao.Idesign.My.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
@ConfigurationProperties("ivanzhao.door")
public class StarterServiceProperties {
    private String userStr;

    public String getUserStr() {
        return userStr;
    }

    public void setUserStr(String userStr) {
        this.userStr = userStr;
    }
}
