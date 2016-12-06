package biz;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import web.biz.impl.DishImpl;
import web.dao.impl.FoodDaoImpl;
import web.dao.impl.FoodTypeDaoImpl;
import web.model.po.Food;
import web.model.po.FoodType;
import web.model.exceptions.NotFoundException;
import web.tools.MyResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public class DishTest {

    private DishImpl dishManage = new DishImpl();

    @Test
    public void test() throws JsonProcessingException, NotFoundException {
        dishManage.setFoodTypeDao(new FoodTypeDaoImpl());
        dishManage.setFoodDao(new FoodDaoImpl());
        Map<String,Object> result = new HashMap<>();
        FoodType foodType = this.dishManage.getDishInType(2);
        List<Food> menus = foodType.getFoodList();

        List<Map<String,String>> foodList = new ArrayList<>();
        for (Food food:menus){
            Map<String,String> oneFood = new HashMap<>();
            oneFood.put("foodid",Integer.toString(food.getId()));
            oneFood.put("name",food.getName());
            oneFood.put("pic",food.getPicture());
            oneFood.put("price",food.getPrice().toString());
            oneFood.put("good_num",Integer.toString(food.getLike()));
            oneFood.put("bad_num",Integer.toString(food.getDislike()));
            foodList.add(oneFood);
        }

        //返回参数
        result.put("list", foodList);
        result.put("pic",foodType.getPicture());
        result.put("name",foodType.getName());
        result.put("num",Integer.toString(foodType.getFoodNum()));

        System.out.println(MyResponse.success(result));
    }

}
