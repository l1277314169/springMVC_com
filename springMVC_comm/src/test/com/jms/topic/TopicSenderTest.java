package com.jms.topic;

import com.po.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application.xml")
public class TopicSenderTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    public TopicSenderTest() {
        System.out.println("aa");
    }

    @Test
    public void testSend() throws Exception {

        User user = new User();
        user.setUsername("nihao");
        user.setId(23);
        jmsTemplate.convertAndSend(user);
    }

    public static void main(String[] args) {
        int[] a = {49, 38, 65, 97, 76, 13, 27, 49};
        int n = a.length;
        for (int i = 0; i < n - 1; ++i) {
            for (int j = 0; j < n - i - 1; ++j) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }

        String str = "cabaabc";
        char[] chars = str.toCharArray();
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            if (!result.contains(String.valueOf(chars[i]))) {
                result += chars[i];
            }
        }
        System.out.println(result);

//       String a = "aaabbcddd";
//        char[] ch = a.toCharArray();
//        int l = 0;String n="";
//        Set<String> set = new HashSet<>();
//        for (int i = 0; i < ch.length; i++)
//        {
//            if(!set.contains(ch[i])){
//                l++;
//                n+=ch[i]+""+l;
//        }
//
//        }
//
//        System.out.println(n);
    }
}