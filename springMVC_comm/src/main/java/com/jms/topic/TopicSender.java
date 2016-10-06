package com.jms.topic;


import com.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @描述 发送消息到队列
 */
public class TopicSender {

    @Autowired
    private JmsTemplate jmsTemplate;//通过@Qualifier修饰符来注入对应的bean

    /**
     * 发送一条消息到指定的队列（目标）
     * @param message 消息内容
     */
    public void send(final String message){
        User user = new User();
        user.setUsername("hao");
        user.setId(23);
        jmsTemplate.convertAndSend(user);
    }

}