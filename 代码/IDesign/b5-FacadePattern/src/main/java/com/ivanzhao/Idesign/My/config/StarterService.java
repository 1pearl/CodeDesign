package com.ivanzhao.Idesign.My.config;

import org.thymeleaf.util.StringUtils;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class StarterService {
    private String userStr;
    public StarterService(String userStr) {
        this.userStr = userStr;
    }

    public String[] split(String separatorChar) {
        return StringUtils.split(this.userStr, separatorChar);
    }
}
