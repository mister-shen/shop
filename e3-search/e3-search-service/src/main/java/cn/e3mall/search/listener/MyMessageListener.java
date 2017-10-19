package cn.e3mall.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author shenrs
 * @Description activemq消息接收监听器
 * @create 2017-10-19 15:34
 **/
public class MyMessageListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("text = [" + text + "]");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
