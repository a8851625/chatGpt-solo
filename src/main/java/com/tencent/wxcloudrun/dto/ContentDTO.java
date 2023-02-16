package com.tencent.wxcloudrun.dto;

import lombok.Data;

/**
 * 会话实体
 */
@Data
public class ContentDTO {

    /**
     * 会话发起来源
     */
    private String fromUser;

    /**
     * gpt响应
     */
    private String response;

    /**
     * gpt请求
     */
    private String request;

    /**
     * gpt请求创建时间
     */
    private String createTime;

    /**
     * gpt请求更行时间
     */
    private String updateTime;

    /**
     * 会话类型
     */
    private String contentType;

}
