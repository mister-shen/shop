package cn.e3mall.item.controller;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shenrs 商品详细查询Controller
 * @Description
 * @create 2017-10-19 17:37
 **/
public class ItemController {


    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model){
        //根据商品id查询商品信息
        TbItem tbItem = itemService.getItemById(itemId);
        //将tbItem对象转为item对象
        Item item = new Item(tbItem);
        //根据商品id查询商品描述
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        //把参数传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        return "item";
    }
}
