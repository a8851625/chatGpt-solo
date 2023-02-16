package com.tencent.wxcloudrun.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： api地址
 *
 * @author https:www.unfbx.com
 * @date 2023-02-11
 */
@Getter
@AllArgsConstructor
public enum ChatGPTUrl {

//    COMPLETIONS("https://api.openai.com/v1/completions"),
    COMPLETIONS("https://xiaohehe.online:8080")
    ;

    private String url;

}
