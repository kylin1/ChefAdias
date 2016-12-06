package web.dao;

import web.model.po.Food;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface FoodDao {

    List<Food> getAllFood();

    /**
     * 获取一种菜品的信息列表
     *
     * @param foodType
     * @return
     */
    List<Food> getFoodOfType(int foodType);

    /**
     * 商家添加菜品
     *
     * @param newFood
     * @return
     */
    MyMessage addFood(Food newFood);

    /**
     * 商家删除菜品
     *
     * @param id
     * @return
     */
    MyMessage deleteFood(int id);

    /**
     * 用户评价菜品/
     * 商家修改菜品信息
     *
     * @param food
     * @return
     */
    MyMessage updateFood(Food food);


}
