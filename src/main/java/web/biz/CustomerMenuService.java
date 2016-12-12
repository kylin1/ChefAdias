package web.biz;

import java.math.BigDecimal;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
public interface CustomerMenuService {

    /**
     * 添加自定义菜单内容
     *
     * @param type 类型1-6
     * @param name 分类菜名
     * @param price 价格
     * @return
     */
    boolean addMMenu(int type, String name, BigDecimal price);

    /**
     * 修改自定义菜单内容
     *
     * @param menuId 目标自定义菜单食物 ID
     * @param type 类型1-6
     * @param name 分类菜名
     * @param price 价格
     * @return
     */
    boolean modMMenu(int menuId, int type, String name, BigDecimal price);


    /**
     * 删除自定义菜单内容
     *
     * @param menuId 目标自定义菜单食物 ID
     * @return
     */
    boolean deleteMMenu(int menuId);
}
