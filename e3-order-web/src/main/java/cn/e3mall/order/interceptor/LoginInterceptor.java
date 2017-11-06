package cn.e3mall.order.interceptor;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shenrs
 * @Description 订单服务登陆拦截器
 * @create 2017-10-30 11:21
 **/
public class LoginInterceptor implements HandlerInterceptor{


    @Autowired
    private  TokenService tokenService;

    @Autowired
    private CartService cartService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @Value("${SSO_URL}")
    private String SSO_URL;

    @Value("${REQUEST_USER_KEY}")
    private String REQUEST_USER_KEY;
    
    @Value("${TT_CART}")
    private String TT_CART;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //从cookie中取token
        String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
        //判断token是否存在
        if (StringUtils.isBlank(token)){
            //如果token不存在，未登录状态，跳转到sso登陆页面。用户登陆成功后，跳转到当前请求的url
            response.sendRedirect(SSO_URL + "/page/login?redirect="+request.getRequestURL());
            //拦截
            return false;
        }
        //如果token存在，需要调用sso系统的服务，根据token取用户信息
        E3Result result = tokenService.getUserByToken(TOKEN_KEY);
        //如果取不到，用户登录以过期，需要重新登录
        if (result.getStatus() != 200){
            //如果token不存在，未登录状态，跳转到sso登陆页面。用户登陆成功后，跳转到当前请求的url
            response.sendRedirect(SSO_URL + "/page/login?redirect="+request.getRequestURL());
            //拦截
            return false;
        }

        //如果取到用户信息，是登录状态，需要把用户信息写入request
        TbUser user = (TbUser) result.getData();
        request.setAttribute(REQUEST_USER_KEY, user);
        //判断cookie中是否有购物车数据，如果有就合并到服务端
        String jsonCartList = CookieUtils.getCookieValue(request, TT_CART);
        if (StringUtils.isNotBlank(jsonCartList)){
            cartService.mergeCart(user.getId(), JsonUtils.jsonToList(jsonCartList, TbItem.class));
        }
        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
