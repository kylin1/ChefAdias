package web.dao;

import web.model.po.FoodExtra;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public interface FoodExtraDao {

    /**
     * 获取一种食物附加的额外食物列表
     * 例如面条可以附加鸡蛋/小菜等
     *
     * @param mainFoodId 主食食物ID
     * @return 附加小菜
     */
    List<FoodExtra> getExtraOfMainFood(int mainFoodId);

    /**
     * 给商家提供的CURD添加方法
     *
     * @param foodExtra
     * @return
     */
    MyMessage addExtraFood(FoodExtra foodExtra);

    FoodExtra getFoodExtra(int id);

    MyMessage updateFoodExtra(FoodExtra foodExtra);

    MyMessage deleteFoodExtra(int id);

}
