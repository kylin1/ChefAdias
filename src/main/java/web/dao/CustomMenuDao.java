package web.dao;

import web.model.po.CustomMenuFood;
import web.tools.MyMessage;

/**
 * 自定义菜单数据层接口
 *
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public interface CustomMenuDao {

    //商家的CURD方法
    CustomMenuFood get(int mmenu_foodid);

    MyMessage add(CustomMenuFood customMenuFood);

    MyMessage update(CustomMenuFood customMenuFood);

    MyMessage delete(int mmenu_foodid);

}
