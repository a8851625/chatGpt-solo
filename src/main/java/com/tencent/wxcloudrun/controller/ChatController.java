package com.tencent.wxcloudrun.controller;

import cn.hutool.json.JSON;
import com.tencent.wxcloudrun.ChatGPTClient;
import com.tencent.wxcloudrun.dto.TextMessageVO;
import com.tencent.wxcloudrun.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@RestController
public class ChatController {

    /**
     * 解析请求消息，post请求
     */
    @PostMapping(value = "/api/chat")
    public void msgProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 调用核心业务类接收消息、处理消息
        String respMessage = processRequest(request);
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }

    public String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {

            //获取消息流,并解析xml
            WxMpXmlMessage message = WxMpXmlMessage.fromXml(request.getInputStream());
            log.info(message.toString());

            // 发送方帐号（open_id）
            String fromUserName = message.getFromUser();
            // 公众帐号
            String toUserName = message.getToUser();
            // 消息类型
            String msgType = message.getMsgType();

            String content = message.getContent();

            // 回复文本消息
            TextMessageVO textMessage = new TextMessageVO();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setMsgType("text");

            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候重试！";

            //chatgpt处理
            ChatGPTClient client = new ChatGPTClient("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");
            textMessage.setContent(client.askQuestion(content));
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理错误了",e);
        }
        return respMessage;
    }

}