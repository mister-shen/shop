package cn.e3mall.cart.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.service.ItemService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shenrs
 * @Description 购物车功能实现类
 * @create 2017-10-26 13:23
 **/
@Controller
public class CartController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @Value("${TT_CART}")
    private String TT_CART;

    @Value("${COOKIE_CART_EXPIRE}")
    private int COOKIE_CART_EXPIRE;

    @Value("${REQUEST_USER_KEY}")
    private String REQUEST_USER_KEY;

    /**
     * 添加商品到购物车
     * @param itemId 商品id
     * @param num 商品数量
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response){
        //判断当前用户是否登录
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_KEY);
        //如果是登录状态将购物车写入redis
        if (user != null){
            //保存到服务端
            cartService.addCart(user.getId(), itemId, num);
            //返回逻辑视图
            return "cartSuccess";
        }

        //1、从cookie中查询商品列表。
        List<TbItem> itemList = this.getCartListFromCookie(request);
        //2、判断商品在商品列表中是否存在。
        boolean hasItem = true;
        for (TbItem tbItem: itemList) {
            //对象比较的是地址，应该是值比较
            if(tbItem.getId() == itemId.longValue()){
                //3、如果存在，商品数量相加。
                tbItem.setNum(tbItem.getNum() + num);
                hasItem = false;
                break;
            }
        }
        //4、不存在，根据商品id查询商品信息。
        if(hasItem){
            TbItem item = itemService.getItemById(itemId);
            //取一张图片
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)){
                String[] images = image.split(",");
                item.setImage(images[0]);
            }
            //设置商品数量
            item.setNum(num);
            //5、把商品添加到购车列表。
            itemList.add(item);
        }
        //6、把购车商品列表写入cookie。
        CookieUtils.setCookie(request,response, TT_CART, JsonUtils.objectToJson(itemList), COOKIE_CART_EXPIRE,true);
        //跳转页面
        return "cartSuccess";
    }

    /**
     * 从cookie中获取购物车列表
     * @param request
     * @return
     */
    public List<TbItem> getCartListFromCookie(HttpServletRequest request){
        //取购物车列表
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        //判断json是否为空
        if(StringUtils.isBlank(json)){
            return new ArrayList<>();
        }
        //把json转换为商品列表返回
        return  JsonUtils.jsonToList(json, TbItem.class);
    }


    /**
     * 查看购物车
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, HttpServletResponse response){
        //获取购物车列表
        List<TbItem> itemList = this.getCartListFromCookie(request);

        //判断当前用户是否登录
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_KEY);
        if (user != null){
            //从cookie中取购物车列表
            //如果不为空，把cookie中的购物车商品与服务端的购物车商品合并
            cartService.mergeCart(user.getId(), itemList);
            //把cookie中的购物车删除
            CookieUtils.deleteCookie(request, response, TT_CART);
            //从服务器端取购物车
            itemList = cartService.getCartList(user.getId());
        }
        //传递给页面
        request.setAttribute("cartList", itemList);
        return "cart";
    }

    /**
     * 更新商品数量
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateNum(@PathVariable Long itemId, @PathVariable Integer num,
                              HttpServletRequest request, HttpServletResponse response){
        //判断当前用户是否登录
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_KEY);
        if (user != null){
            cartService.updateCartNum(user.getId(), itemId, num);
            return E3Result.ok();
        }
        //接收两个参数
        //从cookie中获取商品列表
        List<TbItem> cartList = this.getCartListFromCookie(request);
        //遍历商品找到对应的商品
        for (TbItem item : cartList) {
            if (item.getId() == itemId.longValue()){
                //更新商品数量
                item.setNum(num);
                break;
            }
        }

        //把商品列表写入cookie
        CookieUtils.setCookie(request, response,TT_CART, JsonUtils.objectToJson(cartList),
                COOKIE_CART_EXPIRE, true);
        return E3Result.ok();
    }

    /**
     * 删除购物车中的商品
     * @param itemId 商品id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,
                                 HttpServletRequest request, HttpServletResponse response){
        //判断当前用户是否登录
        TbUser user = (TbUser) request.getAttribute(REQUEST_USER_KEY);
        if (user != null){
            cartService.deleteCartItem(user.getId(), itemId);
            return "redirect:/cart/cart.html";
        }

        //从url中获取商品id
        //从cookie中取购物车列表
        List<TbItem> cartList = this.getCartListFromCookie(request);
        //遍历列表找到对应商品
        for (TbItem item: cartList) {
            if (item.getId() == itemId.longValue()){
                //删除商品
                cartList.remove(item);
                break;
            }
        }
        //把商品列表写入cookie
        CookieUtils.setCookie(request, response,TT_CART, JsonUtils.objectToJson(cartList),
                COOKIE_CART_EXPIRE, true);

        //返回逻辑视图：在逻辑视图中做redirect
        return "redirect:/cart/cart.html";
    }

}
