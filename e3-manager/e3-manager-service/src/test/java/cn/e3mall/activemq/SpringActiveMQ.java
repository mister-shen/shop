package cn.e3mall.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.util.Map;

/**
 * @author shenrs
 * @Description activemq集成spring测试类
 * @create 2017-10-19 14:56
 **/
public class SpringActiveMQ {

    /**
     * @Description 测试点对点发送消息
     * @author shenrs
     * @create 2017/10/19 14:57
     */
//    @Test
    public void testStringActivemq() throws Exception{
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //从spring容器中获取jmstemplate对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //从spring容器中获取Destination对象
        Destination destination = (Destination) applicationContext.getBean("queueDestination");
        //使用jmstemplate对象发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                //创建一个消息对象并返回
                TextMessage message = session.createTextMessage("this is my first spring and activemq text!");
                return message;
            }
        });
    }

    /**
     * @Description 测试点对点发送消息
     * @author shenrs
     * @create 2017/10/19 15:30
     */
//    @Test
    public void testQueueProducer() throws Exception{
        //第一步：初始化一个spring容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //第二步：从容器中获取JMSTemplate对象
        JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
        //第三步：从容器中获取Destination对象
        Queue queue = applicationContext.getBean(ActiveMQQueue.class);
        //第四步：使用jmsTemplate发送消息
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage("text send queue!");
                return message;
            }
        });
    }


}
