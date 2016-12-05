package web.dao;

import web.model.FoodType;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface FoodTypeDao {

    /**
     * 获取所有菜品分类
     *
     * @return
     */
    List<FoodType> getAllFoodType();

    /**
     * 获取一种菜品的分类信息
     *
     * @param id 菜品种类ID
     * @return
     */
    FoodType getFoodType(int id);


    MyMessage addFoodType(FoodType newFoodType);

    MyMessage deleteFoodType(int id);

    MyMessage updateFoodType(FoodType foodType);

}
