package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author shenrs
 * @Description 根据token取用户信息
 * @create 2017-10-25 14:40
 **/
public interface TokenService {

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    E3Result getUserByToken(String token);
}
