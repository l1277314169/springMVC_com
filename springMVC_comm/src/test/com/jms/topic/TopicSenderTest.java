package com.jms.topic;

import com.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class TopicSenderTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testSend() throws Exception {
        User user = new User();
        user.setUsername("hao");
        user.setId(23);
        jmsTemplate.convertAndSend(user);
    }
}