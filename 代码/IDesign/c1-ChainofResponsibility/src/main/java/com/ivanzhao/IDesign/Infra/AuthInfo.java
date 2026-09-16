package com.ivanzhao.IDesign.Infra;

/**
 * 审批结果信息载体实体类
 * 
 * 记录审批流程流转中的状态码与提示信息：
 * - code: "0001" 待审批，"0000" 审批完成；
 * - info: 拼接的流转详情文案（包含单号、状态、当前待办审批人等）。
 */
public class AuthInfo {

    /** 状态码：0001 处理中/待审批，0000 审批完成 */
    private String code;
    
    /** 详细描述信息 */
    private String info = "";

    /**
     * 可变参数构造函数，方便将多个提示文案快速拼接成完整描述
     * 
     * @param code  状态码
     * @param infos 文案片段
     */
    public AuthInfo(String code, String ...infos) {
        this.code = code;
        for (String str : infos) {
            this.info = this.info.concat(str);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}