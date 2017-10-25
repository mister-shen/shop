package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author shenrs
 * @Description
 * @create 2017-10-25 14:41
 **/
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${USER_INFO}")
    private String USER_INFO;

    @Value("${SESSION_EXPIRE}")
    private int SESSION_EXPIRE;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public E3Result getUserByToken(String token) {
        //根据token从redis中获取用户
        String json = jedisClient.get(USER_INFO + token);
        //取不到用户信息，登录已过期，返回登录过期
        if (StringUtils.isBlank(json)){
            return E3Result.build(400, "用户登录已过期");
        }
        //取到用户信息更新token的过期时间
        jedisClient.expire(USER_INFO+token, SESSION_EXPIRE);
        //返回结果，E3Result中包含TbUser对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return E3Result.ok(user);
    }
}
