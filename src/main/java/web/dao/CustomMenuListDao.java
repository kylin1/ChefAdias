package web.dao;

import web.model.po.CustomMenu;
import web.model.po.CustomMenuList;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 12/12/2016.
 * All rights reserved.
 */
public interface CustomMenuListDao {


    /**
     * 在添加自定义菜单的基本信息之后调用
     * 添加菜单里面每个菜品的信息
     *
     * @param list
     * @return
     */
    MyMessage addCustomMenuList(CustomMenuList list);

    /**
     * 获取一个用户的自定义菜单内容
     *
     * @param userId 用户ID
     * @return
     */
    List<CustomMenu> getCustomMenuOfUser(int userId);

    /**
     * 获取一个menu id下面对应的menu list项目
     * 以便获取menu里面的食物+食物数目
     *
     * @param menuId 自定义菜单ID
     * @return
     */
    List<CustomMenuList> getMenuListOfMenu(int menuId);

    /**
     * 删除自定义菜单列表项目
     *
     * @param id
     * @return
     */
    MyMessage deleteCustomMenuList(int id);
}
