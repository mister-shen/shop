package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * 商品管理接口
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
