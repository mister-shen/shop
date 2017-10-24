package cn.e3mall.sso.service.impl;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenrs
 * @Description
 * @create 2017-10-24 16:46
 **/
@Service
public class UserServiceImpl implements UserService{

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
}
