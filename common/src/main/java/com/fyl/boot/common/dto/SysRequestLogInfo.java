package com.fyl.boot.common.dto;

import lombok.Data;

@Data
public class SysRequestLogInfo {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录城市
     */
    private String loginCity;

    /**
     * 服务器地址
     */
    private String serverName;

    /**
     * 服务器端口
     */
    private String serverPort;

    /**
     * 请求uri
     */
    private String requestUri;

    /**
     * 请求URL中参数
     */
    private String requestQueryString;

    /**
     * post数据
     */
    private Object requestPostData;

    /**
     * response数据
     */
    private Object response;

    /**
     * 请求时间
     */
    private String requestTime;

    /**
     * 耗时/毫秒
     */
    private Long spendTimeMillis;

    /**
     * 请求功能名称
     */
    private String resourceName;

    /**
     * 应用服务code
     */
    private String appCode;

}