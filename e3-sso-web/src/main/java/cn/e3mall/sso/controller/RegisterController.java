package cn.e3mall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shenrs
 * @Description 注册功能controller
 * @create 2017-10-24 13:36
 **/
@Controller
public class RegisterController {

    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }
}
