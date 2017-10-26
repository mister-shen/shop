package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shenrs
 * @Description 根据token查询用户信息Controller
 * @create 2017-10-25 14:37
 **/
@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 跨域请求解决方案一
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE/*"application/json;charset=utf-8"*/)
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback){
        E3Result result = tokenService.getUserByToken(token);
        //响应结束前判断是否是jsonp请求
        if (StringUtils.isNotBlank(callback)){
            //把结果封装成一个js语句响应
            return  callback + "("+ JsonUtils.objectToJson(result)+ ");";
        }
        return JsonUtils.objectToJson(result);
    }


    /**
     * 跨域请求解决方案二
     * @param token
     * @param callback
     * @return
     */
   /* @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object getUserByToken1(@PathVariable String token, String callback){
        E3Result result = tokenService.getUserByToken(token);
        //响应结束前判断是否是jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            //把结果封装成一个js语句响应
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return  result;
    }*/

}
