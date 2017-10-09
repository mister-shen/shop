package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

/**
 * 内容分类服务接口
 */
public interface ContentCategoryService {

    /**
     * 获取内容分类列表
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getContentCategoryList(long parentId);

    /**
     * 添加内容分类内容
     * @param parentId
     * @param name
     * @return
     */
    E3Result addContentCategory(long parentId, String name);
}
