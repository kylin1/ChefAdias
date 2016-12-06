package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.DishService;
import web.dao.FoodDao;
import web.dao.FoodTypeDao;
import web.model.po.Food;
import web.model.po.FoodType;
import web.model.exceptions.ErrorCode;
import web.model.exceptions.NotFoundException;
import web.tools.MyMessage;

import java.util.List;

@Service
public class DishImpl implements DishService {

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
    public FoodType getDishInType(int type) throws NotFoundException {
        FoodType foodType = this.foodTypeDao.getFoodType(type);
        if(foodType == null){
            throw new NotFoundException(ErrorCode.NO_TYPE_OF_FOOD);
        }
        return foodType;
    }

    @Override
    public MyMessage addDish(Food food) {
        return this.foodDao.addFood(food);
    }

    public void setFoodDao(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public void setFoodTypeDao(FoodTypeDao foodTypeDao) {
        this.foodTypeDao = foodTypeDao;
    }
}
