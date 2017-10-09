package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品分类服务实现类
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;
    /**
     * 获取商品分类列表
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        //1.取查询参数id parentId
        //2.根据parentId查询 tb_content_category,查询子节点列表
        TbContentCategoryExample contentCategoryExample = new TbContentCategoryExample();
        //设置查询条件
        Criteria criteria = contentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //3.得到List<TbContentCategory>
        List<TbContentCategory> contentCategories = contentCategoryMapper.selectByExample(contentCategoryExample);
        //4.把列表装换成List<EasyUITreeNode>
        List<EasyUITreeNode> easyUITreeNodeList = new ArrayList<>();
        for (TbContentCategory contentCategory: contentCategories) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(contentCategory.getId());
            easyUITreeNode.setText(contentCategory.getName());
            easyUITreeNode.setState(contentCategory.getIsParent()?"closed":"open");
            easyUITreeNodeList.add(easyUITreeNode);
        }
        return easyUITreeNodeList;
    }

    /**
     * 添加内容分类内容
     *
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public E3Result addContentCategory(long parentId, String name) {
        // 1、接收两个参数：parentId、name
        // 2、向tb_content_category表中插入数据。
        // a)创建一个TbContentCategory对象
        TbContentCategory contentCategory = new TbContentCategory();
        // b)补全TbContentCategory对象的属性
        contentCategory.setIsParent(false);
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        Date date = new Date();
        contentCategory.setCreated(date);
        contentCategory.setUpdated(date);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        contentCategory.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        contentCategory.setStatus(1);
        // c)向tb_content_category表中插入数据
        contentCategoryMapper.insert(contentCategory);
        // 3、判断父节点的isparent是否为true，不是true需要改为true。
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()){
            parentNode.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        // 4、需要主键返回。
        // 5、返回E3Result，其中包装TbContentCategory对象
        return E3Result.ok();
    }
}
