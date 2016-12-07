package dao;

import org.junit.Test;
import web.dao.FoodTypeDao;
import web.dao.impl.FoodTypeDaoImpl;
import web.model.po.FoodType;
import web.tools.CheckClass;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class FoodTypeTest {

    private FoodTypeDao  dao = new FoodTypeDaoImpl();

    @Test
    public void testAll() throws ClassNotFoundException {
        //test change
        List<FoodType> list = dao.getAllFoodType();
        for (FoodType foodType : list){
            CheckClass.checkObject("FoodType",foodType);
        }
    }

//    @Test
    public void add() throws ClassNotFoundException {
        FoodType type = new FoodType();
        type.setName("mew");
        type.setPicture("pic");
        this.dao.addFoodType(type);
    }

//    @Test
    public void update() throws ClassNotFoundException {
        FoodType type = dao.getFoodType(6);
        type.setPicture("222");
        type.setName("222333");
        this.dao.updateFoodType(type);
    }

//    @Test
    public void delete() throws ClassNotFoundException {
        this.dao.deleteFoodType(5);
    }
}
