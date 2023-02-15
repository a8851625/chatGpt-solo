package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class TextMessageVO extends BaseMessageVO {

    private String MsgType = "text";

    private String Content;

}
