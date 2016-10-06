package com.jms.queue;


import com.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @描述 发送消息到队列
 */
public class QueueSender {

    @Autowired
    private JmsTemplate jmsTemplate;//通过@Qualifier修饰符来注入对应的bean

    /**
     * 发送一条消息到指定的队列（目标）
     * @param message 消息内容
     */
    @RequestMapping(value = "/send")

    public void send(final String message){
        User user = new User();
        user.setUsername("hao");
        user.setId(23);
        jmsTemplate.convertAndSend(user);
    }

}