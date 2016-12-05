package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.IDishManage;
import web.dao.FoodDao;
import web.dao.FoodTypeDao;
import web.model.Food;
import web.model.FoodType;
import web.model.exceptions.ErrorCode;
import web.model.exceptions.NotFoundException;
import web.tools.MyMessage;

import java.util.List;

@Service
public class DishManage implements IDishManage {

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private FoodTypeDao foodTypeDao;


    @Override
    public List<Food> getAllDish() {
        return this.foodDao.getAllFood();
    }

    @Override
    public List<FoodType> getMenuCategory() {
        return this.foodTypeDao.getAllFoodType();
    }

    @Override
    public List<Food> getDishInType(int type) throws NotFoundException {
        List<FoodType> dishMenus = this.getMenuCategory();
        for (FoodType oneMenu:dishMenus){
            //找到目标菜品种类
            int id = oneMenu.getId();
            if(id == type){
                //寻找菜品种类下面的所有菜品信息
                return foodDao.getFoodOfType(id);
            }
        }
        throw new NotFoundException(ErrorCode.NO_TYPE_OF_FOOD);
    }

    @Override
    public MyMessage addDish(Food food) {
        return this.foodDao.addFood(food);
    }

}
