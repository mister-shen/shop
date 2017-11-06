package cn.e3mall.order.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * @author shenrs
 * @Description 订单服务接口
 * @create 2017-10-30 13:29
 **/
public interface OrderService {

    /**
     * 生成订单信息
     * @param orderInfo
     * @return
     */
    E3Result createOrder(OrderInfo orderInfo);
}
