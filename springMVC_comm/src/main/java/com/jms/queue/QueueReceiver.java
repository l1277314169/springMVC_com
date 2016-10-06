package com.jms.queue;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.po.User;
import org.springframework.stereotype.Component;

/**
 * @作者 Goofy
 * @邮件 252878950@qq.com
 * @日期 2014-4-1上午10:11:51
 * @描述 队列消息监听器
 */

public class QueueReceiver  {


    public void receiveMessage(User message) {
        System.out.println("ConsumerListener通过receiveMessage接收到一个纯文本消息，消息内容是：" + message.getUsername());
    }

}