package web.dao;

import web.model.po.CustomMenu;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public interface CustomMenuDao {

    /**
     * 添加自定义菜单
     * 在customMenu中会自动set新的ID
     *
     * @param customMenu
     * @return
     */
    MyMessage addCustomMenu(CustomMenu customMenu);

    /**
     * 获取一个用户的自定义菜单内容
     *
     * @param userId 用户ID
     * @return
     */
    List<CustomMenu> getCustomMenuOfUser(int userId);

    /**
     * 删除自定义菜单
     *
     * @param id
     * @return
     */
    MyMessage deleteCustomMenu(int id);

    /**
     * 更新自定义菜单
     *
     * @param customMenu
     * @return
     */
    MyMessage updateCustomMenu(CustomMenu customMenu);

}
