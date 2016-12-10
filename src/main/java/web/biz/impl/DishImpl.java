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
import web.model.vo.FoodVO;
import web.model.vo.FoodTypeBasicVO;
import web.model.vo.FoodTypeVO;
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
    public List<FoodVO> getAllDish() {
        List<Food> foods =  foodDao.getAllFood();
        return null;
    }

    @Override
    public List<FoodTypeBasicVO> getMenuCategory() {
        List<FoodType> foodTypes = foodTypeDao.getAllFoodType();
        List<FoodTypeBasicVO> foodTypeList = new ArrayList<>();
        for (FoodType foodType : foodTypes) {
            int num = foodDao.getFoodOfType(foodType.getId()).size();
            FoodTypeBasicVO vo = new FoodTypeBasicVO(foodType.getId() + "", foodType.getPicture(), foodType.getName(), num);
            foodTypeList.add(vo);
        }
        return foodTypeList;
    }

    @Override
    public FoodTypeVO getDishInType(int typeID) throws NotFoundException {
        FoodType foodType = foodTypeDao.getFoodType(typeID);
        if (foodType == null) {
            throw new NotFoundException(ErrorCode.NO_TYPE_OF_FOOD);
        }
        List<Food> foodList = foodDao.getFoodOfType(typeID);
        List<FoodVO> foodInfoList = new ArrayList<>();
        for (Food food : foodList) {
            FoodVO foodVO = new FoodVO(food.getId() + "", food.getName(), food.getPicture(), food.getPrice(), food.getLike(), food.getDislike());
            foodInfoList.add(foodVO);
        }
        return new FoodTypeVO(foodType.getPicture(), foodType.getName(), foodList.size(), foodInfoList);
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
