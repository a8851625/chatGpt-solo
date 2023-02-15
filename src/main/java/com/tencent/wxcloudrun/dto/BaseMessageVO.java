package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class BaseMessageVO {

    private String ToUserName;

    private String FromUserName;

    private long CreateTime;

    private int FuncFlag = 0;

}