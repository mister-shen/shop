package cn.e3mall.content.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 首页内容管理服务实现类
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    
    @Value("${CONTENT_LIST}")
    private  String CONTENT_LIST;
    
    @Autowired
    private JedisClient jedisClient;
    
    /**
     * 添加内容详细
     *
     * @param content
     * @return
     */
    @Override
    public E3Result addContent(TbContent content) {
        //补全属性
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        //插入数据
        contentMapper.insertSelective(content);

        //缓存同步
        try {
            jedisClient.hdel(CONTENT_LIST, content.getCategoryId()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return E3Result.ok();
    }

    /**
     * 根据内容分类id查询内容列表
     *
     * @param cid
     * @return
     */
    @Override
    public List<TbContent> getContentListByCid(long cid) {
        //查询缓存
        try {
            String json = jedisClient.hget(CONTENT_LIST, cid + "");
            //判断json是否为空
            if(StringUtils.isNotBlank(json)){
                //把json装换成list
                List<TbContent> contents = JsonUtils.jsonToList(json, TbContent.class);
                return  contents;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //根据cid查询数据
        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        //设置查询条件
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(contentExample);
        try {
            //向缓存中添加数据
            jedisClient.hset(CONTENT_LIST, cid+"", JsonUtils.objectToJson(list));
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

}
