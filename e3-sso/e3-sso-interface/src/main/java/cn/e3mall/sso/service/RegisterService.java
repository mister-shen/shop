package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;

/**
 * @author shenrs
 * @Description 注册服务类
 * @create 2017-10-24 16:43
 **/
public interface RegisterService {

    /**
     * 校验用户名、电话号码、邮箱是否重复
     * @param param 校验值
     * @param type 类型：1 用户名，2 电话号码，3 邮箱
     * @return
     */
    E3Result checkData(String param, int type);

    /**
     * 注册用户信息
     * @param tbUser
     * @return
     */
    E3Result register(TbUser tbUser);
}
