package com.jms;


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
@Component
@RequestMapping("/queue")
public class QueueSender {

    @Autowired
    private JmsTemplate jmsTemplate;//通过@Qualifier修饰符来注入对应的bean

    /**
     * 发送一条消息到指定的队列（目标）
     * @param queueName 队列名称
     * @param message 消息内容
     */
    @RequestMapping(value = "/send")
    public void send(String queueName,final String message){
        jmsTemplate.send("test.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("你好！");
            }
        });
    }

}