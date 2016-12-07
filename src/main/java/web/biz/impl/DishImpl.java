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
import web.model.vo.FoodInfoVO;
import web.model.vo.FoodTypeVO;
import web.model.vo.FoodVO;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishImpl implements DishService {

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private FoodTypeDao foodTypeDao;

    @Override
    public List<Food> getAllDish() {
        return foodDao.getAllFood();
    }

    @Override
    public List<FoodTypeVO> getMenuCategory() {
        List<FoodType> foodTypes = foodTypeDao.getAllFoodType();
        List<FoodTypeVO> foodTypeList = new ArrayList<>();
        for (FoodType foodType : foodTypes) {
            int num = foodDao.getFoodOfType(foodType.getId()).size();
            FoodTypeVO vo = new FoodTypeVO(foodType.getId() + "", foodType.getPicture(), foodType.getName(), num);
            foodTypeList.add(vo);
        }
        return foodTypeList;
    }

    @Override
    public FoodVO getDishInType(int typeID) throws NotFoundException {
        FoodType foodType = foodTypeDao.getFoodType(typeID);
        if (foodType == null) {
            throw new NotFoundException(ErrorCode.NO_TYPE_OF_FOOD);
        }
        List<Food> foodList = foodDao.getFoodOfType(typeID);
        List<FoodInfoVO> foodInfoList = new ArrayList<>();
        for (Food food : foodList) {
            FoodInfoVO foodInfoVO = new FoodInfoVO(food.getId() + "", food.getName(), food.getPicture(), food.getPrice(), food.getLike(), food.getDislike());
            foodInfoList.add(foodInfoVO);
        }
        return new FoodVO(foodType.getPicture(), foodType.getName(), foodList.size(), foodInfoList);
    }

    @Override
    public MyMessage addDish(Food food) {
        return foodDao.addFood(food);
    }

    public void setFoodDao(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public void setFoodTypeDao(FoodTypeDao foodTypeDao) {
        this.foodTypeDao = foodTypeDao;
    }
}
