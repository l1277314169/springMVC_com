package jms.queue;

import com.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application.xml")
public class QueueSenderTest {

    @Autowired
    private JmsTemplate jmsTemplate;//通过@Qualifier修饰符来注入对应的bean


    @Test
    public void testSend() throws Exception {
        User user = new User();
        user.setUsername("hao");
        user.setId(23);
        jmsTemplate.convertAndSend(user);
    }
}