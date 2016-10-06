package jms.queue;

import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import static org.junit.Assert.*;

public class QueueReceiverTest implements MessageListener {


    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("QueueReceiver1接收到消息:"+((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}