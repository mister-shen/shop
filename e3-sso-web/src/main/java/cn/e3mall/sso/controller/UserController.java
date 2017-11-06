package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shenrs
 * @Description 用户登录处理
 * @create 2017-10-24 17:15
 **/
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password,
                          HttpServletRequest request, HttpServletResponse response){
        //1.接受两个参数
        //2.调用service进行登录
        E3Result result = userService.userLogin(username, password);
        //3.从结果中获取token，写入cookie，cookie要跨域
        Object data = result.getData();
        if(data != null){
            String token = data.toString();
            CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        }

        //4.响应数据，json数据，e3result，其中包含token
        return result;
    }

}
