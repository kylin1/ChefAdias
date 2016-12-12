package biz;

import com.fasterxml.jackson.core.JsonProcessingException;
import web.biz.impl.DishImpl;
import web.dao.impl.FoodDaoImpl;
import web.dao.impl.FoodExtraDaoImpl;
import web.dao.impl.FoodTypeDaoImpl;
import web.model.exceptions.NotFoundException;
import web.model.po.Food;
import web.model.vo.FoodVO;
import web.tools.MyResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public class DishTest {

    private DishImpl dishManage = new DishImpl();

    public DishTest(){
        dishManage.setFoodTypeDao(new FoodTypeDaoImpl());
        dishManage.setFoodDao(new FoodDaoImpl());
        dishManage.setFoodExtraDao(new FoodExtraDaoImpl());
    }

//    @Test
    public void test() throws JsonProcessingException, NotFoundException {


        List<FoodVO> foodVOS = dishManage.getAllDish();
        for (FoodVO foodVO:foodVOS){
            System.out.print(foodVO.getFoodid()+" ");
            System.out.println(foodVO.getExtraFood().size());
        }

        System.out.println( MyResponse.success(foodVOS));

    }

//    @Test
    public void add(){
        Food food = new Food();
        food.setDescription("test");
        food.setDislike(11);
        food.setLike(2);
        food.setName("test name");
        food.setPicture("pic");
        food.setPrice(new BigDecimal("12.3"));
        food.setType_id(2);
        dishManage.addDish(food);
    }

}
