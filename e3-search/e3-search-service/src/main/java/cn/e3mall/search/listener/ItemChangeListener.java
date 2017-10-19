package cn.e3mall.search.listener;

import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.search.service.impl.SearchItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author shenrs
 * @Description 商品信息变更监听器
 * @create 2017-10-19 16:52
 **/
public class ItemChangeListener implements MessageListener{

    @Autowired
    private SearchItemServiceImpl searchItemServiceImpl;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            //向索引库添加文档
            searchItemServiceImpl.addDocument(Long.parseLong(textMessage.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
