package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shenrs
 * @Description 初始化spring容器
 * @create 2017-10-19 16:24
 **/
public class InitApplication {

    /**
     * @Description 初始化spring容器：activemq点对点消费者
     * @author shenrs
     * @create 2017/10/19 16:26
     */
//    @Test
    public void testQueueConsumer() throws Exception{
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //等待键盘输入
        System.in.read();
    }
}
