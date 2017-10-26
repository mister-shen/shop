package cn.e3mall.cart.interceptor;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
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
 * @Description 用户登录处理拦截器
 * @create 2017-10-26 14:33
 **/
public class LoginInterceptor implements HandlerInterceptor{

    @Autowired
    private TokenService tokenService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @Value("${REQUEST_USER_KEY}")
    private String REQUEST_USER_KEY;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
        //前处理，执行handle之前执行此方法
        //返回true:放行， false：拦截
        //1.从cookie中取token
        String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
        //2.如果没有token, 未登录状态，直接放行
        if(StringUtils.isBlank(token)){
            return true;
        }
        //3.取到token,需要调用sso系统的服务，根据token取用户信息
        E3Result result = tokenService.getUserByToken(token);
        //4.没有取到用户，登录过期，直接放行
        if (result.getStatus() != 200){
            return true;
        }
        //5.取到用户信息，登录状态
        TbUser user = (TbUser) result.getData();
        //6.将用户信息发放到request中，只需要在Controller中判断request中是否包含user信息，放行
        request.setAttribute(REQUEST_USER_KEY, user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView) throws Exception {
        //handle之后，返回ModelAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception e) throws Exception {
        //完成处理，返回ModelAndView之后
        //可以在此处理异常
    }
}
