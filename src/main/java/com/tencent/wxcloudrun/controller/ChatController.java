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

    private WxMpXmlMessage message;

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
        try {
            //获取消息流,并解析xml
            message = WxMpXmlMessage.fromXml(request.getInputStream());
            log.info(message.toString());

            String content = message.getContent();

            String key= "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";
            //chatgpt处理
            ChatGPTClient client = new ChatGPTClient(key);
            return MessageUtil.textMessageToXml(buildResponse(message,client.askQuestion(content)));
        } catch (Exception e) {
            log.error("处理错误了",e);
        }
        return MessageUtil.textMessageToXml(buildResponse(message,"西八"));
    }

    /**
     * 文本消息返回体
     * @param message
     * @param returnContent
     * @return
     */
    private TextMessageVO buildResponse(WxMpXmlMessage message,String returnContent){
        TextMessageVO textMessage = new TextMessageVO();
        textMessage.setToUserName(message.getFromUser());
        textMessage.setFromUserName(message.getToUser());
        textMessage.setCreateTime(System.currentTimeMillis());
        textMessage.setMsgType("text");
        textMessage.setContent(returnContent);
        return textMessage;
    }

}