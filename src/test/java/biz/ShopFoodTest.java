package biz;

import org.junit.Test;
import web.biz.impl.ShopFoodImpl;
import web.dao.impl.FoodDaoImpl;
import web.dao.impl.FoodExtraDaoImpl;
import web.dao.impl.FoodTypeDaoImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 10/12/2016.
 * All rights reserved.
 */
public class ShopFoodTest {

    private ShopFoodImpl shopFood = new ShopFoodImpl();

    public ShopFoodTest(){
        shopFood.setFoodDao(new FoodDaoImpl());
        shopFood.setFoodExtraDao(new FoodExtraDaoImpl());
        shopFood.setFoodTypeDao(new FoodTypeDaoImpl());
    }

    @Test
    public void updateType() {
        List<String> exlist = new ArrayList<>();
        exlist.add("2");
        exlist.add("3");
        exlist.add("4");
        shopFood.modFood(1,"my 1",new BigDecimal("10"),exlist,"des");
    }
}
