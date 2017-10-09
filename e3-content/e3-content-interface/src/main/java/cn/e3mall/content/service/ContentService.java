package cn.e3mall.content.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

/**
 * 内容管理接口类
 */
public interface ContentService {

    /**
     * 添加内容详细
     * @param content
     * @return
     */
    E3Result addContent(TbContent content);

    /**
     * 根据内容分类id查询内容列表
     * @param cid
     * @return
     */
    List<TbContent> getContentListByCid(long cid);
}
