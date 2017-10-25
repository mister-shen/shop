package cn.e3mall.sso.service.impl;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author shenrs
 * @Description 注册功能服务层
 * @create 2017-10-24 16:46
 **/
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;

    @Override
    public E3Result checkData(String param, int type) {
        //1.从tb_user表查询数据
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //2.查询条件根据type参数动态生成
        //1,2,3 分别代码 username phone email
        if(type == 1){
            criteria.andUsernameEqualTo(param);
        }else if(type == 2){
            criteria.andPhoneEqualTo(param);
        }else if(type == 3){
            criteria.andEmailEqualTo(param);
        }else{
            return E3Result.build(400, "非法的参数");
        }

        List<TbUser> userList = userMapper.selectByExample(example);
        //3.判断查询结果，如果查询到数据返回false
        if(userList != null && userList.size() !=0){
            //4.如果有结果，返回false
            return E3Result.ok(false);
        }
        //5.使用e3result包装结果返回
        return E3Result.ok(true);
    }

    @Override
    public E3Result register(TbUser tbUser) {
        //1.使用tbUser接受提交的请求
            //验证必填项数据是否为空
        if(StringUtils.isBlank(tbUser.getUsername())){
            return  E3Result.build(400, "用户名不能为空");
        }
        if(StringUtils.isBlank(tbUser.getPassword())){
            return  E3Result.build(400, "密码不能为空");
        }


        E3Result result = this.checkData(tbUser.getUsername(), 1);
        if(!(boolean)result.getData()){
            return E3Result.build(400, "此用户名已被使用");
        }

        if(StringUtils.isNotBlank(tbUser.getPhone())){
            result = this.checkData(tbUser.getPhone(), 2);
            if(!(boolean)result.getData()){
                return E3Result.build(400, "此手机号已被使用");
            }
        }

        if(StringUtils.isNotBlank(tbUser.getEmail())){
            result = this.checkData(tbUser.getEmail(), 3);
            if(!(boolean)result.getData()){
                return E3Result.build(400, "此邮箱地址已被使用");
            }
        }

        //2.补全tbUser其他属性
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        //3.密码要进行MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5Pass);
        //4.把用户插入到数据库中
        userMapper.insert(tbUser);
        //5.返回E3Result
        return E3Result.ok();
    }
}
