package dao;

import org.junit.Test;
import web.dao.FoodDao;
import web.dao.impl.FoodDaoImpl;
import web.model.po.Food;
import web.tools.CheckClass;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class FoodTest {

    private FoodDao dishDao = new FoodDaoImpl();

//    @Test
    public void testAll(){
        for(int i = 1;i<=4;i++){
            Food food = new Food();
            food.setName("pizza");
            food.setPicture("http://139.196.179.145/images/pizza.jpg");
            food.setPrice(new BigDecimal("8"));

            food.setType_id(4);
            food.setDescription("delicious pizza");

            food.setLike(34);
            food.setDislike(3);

            dishDao.addFood(food);
        }
    }

    @Test
    public void testFoodOfType() throws ClassNotFoundException {
//        List<Food> list = dishDao.getFoodOfType(2);
        List<Food> list = dishDao.getAllFood();
        this.printFoodList(list);
    }

    private void printFoodList(List<Food> list) throws ClassNotFoundException {
        for (Food food:list){
            CheckClass.checkObject("Food",food);
        }
    }


    @Test
    public void testFood() throws ClassNotFoundException {
//        List<Food> list = dishDao.getFoodOfType(2);
        Food food = dishDao.getFood(1);
        CheckClass.checkObject("Food",food);
    }

//    @Test
    public void testAddFood() throws ClassNotFoundException {
        Food food = new Food();
        food.setDescription("test");
        food.setDislike(11);
        food.setLike(2);
        food.setName("test name");
        food.setPicture("pic");
        food.setPrice(new BigDecimal("12.3"));
        food.setType_id(2);
        dishDao.addFood(food);
    }

//    @Test
    public void testUpdateFood() throws ClassNotFoundException {
        Food food = dishDao.getFood(15);
        food.setDescription("test 333");
        food.setDislike(1331);
        food.setLike(3332);
        food.setName("test name  223332");
        food.setPicture("pic 223332");
        food.setPrice(new BigDecimal("12333.3"));
        food.setType_id(3);
        dishDao.updateFood(food);
    }

//    @Test
    public void delete() throws ClassNotFoundException {
        dishDao.deleteFood(15);
    }


}
