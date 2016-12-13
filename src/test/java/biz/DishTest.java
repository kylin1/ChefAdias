package biz;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import web.biz.impl.DishImpl;
import web.dao.impl.FoodDaoImpl;
import web.dao.impl.FoodExtraDaoImpl;
import web.dao.impl.FoodTypeDaoImpl;
import web.model.exceptions.NotFoundException;
import web.model.po.Food;
import web.model.vo.FoodVO;
import web.tools.MyResponse;

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

    @Test
    public void test() throws JsonProcessingException, NotFoundException {
        Food mainFood = new Food();
        mainFood.setId(1);

        List<FoodVO> foodVOS = dishManage.getExtraFoodList(mainFood);
        for (FoodVO foodVO:foodVOS){
            System.out.print(foodVO.getFoodid()+" ");
        }

        System.out.println( MyResponse.success(foodVOS));

    }

}
