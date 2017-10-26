package cn.e3mall.cart.service.impl;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenrs
 * @Description
 * @create 2017-10-26 15:03
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    @Override
    public E3Result addCart(long userId, long itemId, int num) {
        //向redis中添加购物车
        //数据类型是hash key：用户id field:商品id value:商品信息
        //判断商品是否存在
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        //如果存在数量相加
        if (StringUtils.isNotBlank(json)) {
            //把json转换成tbItem
            TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
            tbItem.setNum(tbItem.getNum()+num);
            //写回redis
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
            return E3Result.ok();
        }
        //如果商品不存在，根据商品id取商品信息
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        //设置购物车商品数量
        tbItem.setNum(num);
        //取一张图片
        String image = tbItem.getImage();
        if (StringUtils.isNotBlank(image)){
            String[] images = image.split(",");
            tbItem.setImage(images[0]);
        }

        //添加到购物车列表
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(long userId, List<TbItem> itemList) {
        //遍历商品列表
        //把列表添加到购物车。
        //判断购物车中是否有此商品
        //如果有，数量相加
        //如果没有添加新的商品
        for (TbItem item: itemList) {
            this.addCart(userId, item.getId(), item.getNum());
        }
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(long userId) {
        //根据用户id查询购车列表
        List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for (String json:jsonList) {
            //将商品添加到商品list
            itemList.add(JsonUtils.jsonToPojo(json, TbItem.class));
        }
        return itemList;
    }

    @Override
    public E3Result updateCartNum(long userId, long itemId, int num) {
        //从redis中获取商品
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        //设置商品数量
        TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
        item.setNum(num);
        //写入redis
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));

        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(long userId, long itemId) {
        //删除购物车商品
        jedisClient.hdel(REDIS_CART_PRE+":"+userId, itemId+"");
        return E3Result.ok();
    }


}
