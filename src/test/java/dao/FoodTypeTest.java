package dao;

import org.junit.Test;
import web.dao.FoodTypeDao;
import web.dao.impl.FoodTypeDaoImpl;
import web.model.FoodType;
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
        List<FoodType> list = dao.getAllFoodType();
        for (FoodType foodType : list){
            CheckClass.checkObject("FoodType",foodType);
        }
    }
}
