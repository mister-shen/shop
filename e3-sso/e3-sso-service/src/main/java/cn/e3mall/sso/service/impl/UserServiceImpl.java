package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author shenrs
 * @Description 用户服务实现类
 * @create 2017-10-25 14:02
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_INFO}")
    private String USER_INFO;

    @Value("${SESSION_EXPIRE}")
    private int SESSION_EXPIRE;

    @Override
    public E3Result userLogin(String username, String password) {
        //1、判断用户名密码是否正确。
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> userList = userMapper.selectByExample(example);
        if (userList == null || userList.size() == 0){
            return E3Result.build(400, "用户名或密码错误");
        }

        TbUser user = userList.get(0);
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!md5Pass.equals(user.getPassword())){
            return E3Result.build(400, "用户名或密码错误");
        }

        //2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
        String token = UUID.randomUUID().toString();
        //3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
        //4、使用String类型保存Session信息。可以使用“前缀:token”为key
        user.setPassword(null);
        jedisClient.set(USER_INFO+token, JsonUtils.objectToJson(user));
        //5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
        jedisClient.expire(USER_INFO+token, SESSION_EXPIRE);

        //6、返回e3Result包装token。
        return E3Result.ok(token);
    }
}
