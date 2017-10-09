package cn.e3mall.content.service.impl;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
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
    /**
     * 添加内容详细
     *
     * @param content
     * @return
     */
    @Override
    public E3Result addContent(TbContent content) {
        //补全属性\
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        //插入数据
        contentMapper.insertSelective(content);
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
        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        //设置查询条件
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        return contentMapper.selectByExampleWithBLOBs(contentExample);
    }

}
