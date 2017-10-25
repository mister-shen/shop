package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author shenrs
 * @Description 用户服务接口
 * @create 2017-10-25 14:00
 **/
public interface UserService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    E3Result userLogin(String username, String password);
}
