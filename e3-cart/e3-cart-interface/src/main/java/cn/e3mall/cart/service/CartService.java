package cn.e3mall.cart.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

import java.util.List;

/**
 * @author shenrs
 * @Description 购物车服务接口
 * @create 2017-10-26 14:59
 **/
public interface CartService {

    /**
     * 添加商品到购物车--保存到redis
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    E3Result addCart(long userId, long itemId, int num);

    /**
     * 合并cookie购物车与redis中的购物车
     * @param userId
     * @param itemList
     * @return
     */
    E3Result mergeCart(long userId, List<TbItem> itemList);

    /**
     * 从redis中获取购物车列表
     * @param userId
     * @return
     */
    List<TbItem> getCartList(long userId);

    /**
     * 更新商品数量
     * @param userId 用户id
     * @param itemId 商品id
     * @param num 商品数量
     * @return
     */
    E3Result updateCartNum(long userId, long itemId, int num);

    /**
     * 删除购物车中的商品
     * @param userId
     * @param itemId
     * @return
     */
    E3Result deleteCartItem(long userId, long itemId);
}
