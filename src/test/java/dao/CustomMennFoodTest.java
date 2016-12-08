package dao;

import web.dao.CustomMenuFoodDao;
import web.dao.impl.CustomMenuFoodDaoImpl;
import web.model.po.CustomMenuFood;
import web.tools.CheckClass;

import java.math.BigDecimal;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public class CustomMennFoodTest {

    private CustomMenuFoodDao dao = new CustomMenuFoodDaoImpl();

//    @Test
    public void get() throws ClassNotFoundException {
        CustomMenuFood customMenuFood = dao.get(1);
        CheckClass.checkObject("CustomMenuFood", customMenuFood);
    }

//    @Test
//    public void getAll() throws ClassNotFoundException {
//        List<FoodExtra> list = dao.getExtraOfMainFood(1);
//        for (FoodExtra foodExtra:list){
//            CheckClass.checkObject("FoodExtra",foodExtra);
//        }
//    }

//    @Test
    public void add() {
        CustomMenuFood food = new CustomMenuFood();
        food.setName("test");
        food.setPrice(new BigDecimal("55.5"));
        food.setType(5);
        dao.add(food);
    }

//    @Test
    public void update() {
        CustomMenuFood food = dao.get(5);
        food.setName("test234");
        food.setPrice(new BigDecimal("43.5"));
        food.setType(6);
        dao.update(food);
    }

//    @Test
    public void delete() {
        dao.delete(5);
    }
}
