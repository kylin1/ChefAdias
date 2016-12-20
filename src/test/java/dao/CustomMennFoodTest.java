package dao;

import org.junit.Test;
import web.dao.CustomMenuFoodDao;
import web.dao.impl.CustomMenuFoodDaoImpl;
import web.model.po.CustomMenuFood;
import web.tools.CheckClass;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public class CustomMennFoodTest {

    private CustomMenuFoodDao dao = new CustomMenuFoodDaoImpl();

//    @Test
    public void get() throws ClassNotFoundException {
        List<CustomMenuFood> list = dao.getCustomMenuFood();
        for (CustomMenuFood customMenuFood : list)
            CheckClass.checkObject("CustomMenuFood", customMenuFood);
    }

//    @Test
//    public void getAll() throws ClassNotFoundException {
//        List<FoodExtra> list = dao.getExtraOfMainFood(1);
//        for (FoodExtra foodExtra:list){
//            CheckClass.checkObject("FoodExtra",foodExtra);
//        }
//    }

    @Test
    public void add() {
        CustomMenuFood food = new CustomMenuFood();
        food.setName("test");
        food.setPrice(new BigDecimal("55.5"));
        food.setType(5);
        food.setPicture("pic ");
        dao.add(food);
        System.out.println(food.getId());
    }

//    @Test
    public void update() {
        CustomMenuFood food = dao.get(14);
        food.setName("test234");
        food.setPrice(new BigDecimal("43.5"));
        food.setType(6);
        food.setPicture("pic 2");
        dao.update(food);
    }

//    @Test
    public void delete() {
        dao.delete(14);
    }
}
