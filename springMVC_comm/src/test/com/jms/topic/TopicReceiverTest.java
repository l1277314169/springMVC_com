package com.jms.topic;

import com.po.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class TopicReceiverTest {

    @Test
    public void testReceiveMessage(User message) throws Exception {
            System.out.println("ConsumerListener通过receiveMessage接收到一个纯文本消息，消息内容是：" + message.getUsername());
    }
}