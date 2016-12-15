package dao;

import web.dao.FoodExtraDao;
import web.dao.impl.FoodExtraDaoImpl;
import web.model.po.FoodExtra;
import web.tools.CheckClass;

import java.util.List;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public class FoodExtraTest {

    private FoodExtraDao dao = new FoodExtraDaoImpl();

//    @Test
    public void get() throws ClassNotFoundException {
        FoodExtra foodExtra = dao.getFoodExtra(2);
        CheckClass.checkObject("FoodExtra",foodExtra);
    }

//    @Test
    public void getAll() throws ClassNotFoundException {
        List<FoodExtra> list = dao.getExtraOfMainFood(2);
        for (FoodExtra foodExtra:list){
            CheckClass.checkObject("FoodExtra",foodExtra);
        }
    }

//    @Test
    public void add() {
        FoodExtra foodExtra = new FoodExtra();
        foodExtra.setFood_id(2);
        foodExtra.setExtra_food_id(3);
        dao.addExtraFood(foodExtra);
    }

//    @Test
    public void update() {
        FoodExtra foodExtra = dao.getFoodExtra(2);
        foodExtra.setFood_id(5);
        foodExtra.setExtra_food_id(6);
        dao.updateFoodExtra(foodExtra);
    }

//    @Test
    public void delete() {
        dao.deleteFoodExtra(2);
    }

}
