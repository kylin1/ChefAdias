package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.biz.ShopFoodService;
import web.dao.FoodDao;
import web.dao.FoodExtraDao;
import web.dao.FoodTypeDao;
import web.model.exceptions.ErrorCode;
import web.model.exceptions.ServerException;
import web.model.po.Food;
import web.model.po.FoodExtra;
import web.model.po.FoodType;
import web.tools.MyFile;
import web.tools.MyMessage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
@Service
public class ShopFoodImpl implements ShopFoodService {

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private FoodTypeDao foodTypeDao;

    @Autowired
    private FoodExtraDao foodExtraDao;

    @Override
    public int addFood(String name, int typeID, BigDecimal price, List<String> foodIDList, String description) throws ServerException {
        Food food = new Food();
        food.setName(name);
        food.setType_id(typeID);
        food.setPrice(price);
        food.setLike(0);
        food.setDislike(0);
        food.setDescription(description);

        MyMessage myMessage = foodDao.addFood(food);
        int intFoodID = food.getId();

        FoodExtra foodExtra = new FoodExtra();
        for (String extraID : foodIDList) {
            int intExtraID = Integer.parseInt(extraID);
            foodExtra.setExtra_food_id(intExtraID);
            foodExtra.setFood_id(intFoodID);
            foodExtraDao.addExtraFood(foodExtra);
        }

        if(myMessage.isSuccess())
            return intFoodID;
        else
            throw new ServerException(ErrorCode.SERVER);
    }

    @Override
    public MyMessage addFoodPic(int foodID, MultipartFile pic) {
        Food food = foodDao.getFood(foodID);
        String newPath = "";
        try {
            newPath = MyFile.saveFile(pic);
            food.setPicture(newPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodDao.updateFood(food);
    }

    @Override
    public MyMessage deleteFood(int foodID) {
        return foodDao.deleteFood(foodID);
    }

    @Override
    public MyMessage modFood(int foodID, String name, BigDecimal price, List<String> foodIDList, String description) {
        Food food = foodDao.getFood(foodID);
        food.setName(name);
        food.setPrice(price);
        food.setDescription(description);
        MyMessage myMessage = foodDao.updateFood(food);

        // TODO 新的extra需要与旧的对比/删除/添加

        // old extra food of this food
        List<FoodExtra> oldExtraList = this.foodExtraDao.getExtraOfMainFood(foodID);
        for (FoodExtra oldExtra:oldExtraList){
            int id = oldExtra.getId();
            // delete old ones
            this.foodExtraDao.deleteFoodExtra(id);
        }

        // new extra food of this food
        for (String extraID : foodIDList) {
            FoodExtra foodExtra = new FoodExtra();
            foodExtra.setFood_id(foodID);
            int intExtraID = Integer.parseInt(extraID);
            foodExtra.setExtra_food_id(intExtraID);
            // add new ones
            foodExtraDao.addExtraFood(foodExtra);
        }
        return myMessage;
    }

    @Override
    public MyMessage addType(String name, MultipartFile pic) {
        FoodType foodType = new FoodType();
        foodType.setName(name);
        try {
            String newPath = MyFile.saveFile(pic);
            foodType.setPicture(newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodTypeDao.addFoodType(foodType);
    }

    @Override
    public MyMessage deleteType(int typeID) {
        return foodTypeDao.deleteFoodType(typeID);
    }

    @Override
    public MyMessage modType(int typeID, String name, MultipartFile pic) {
        FoodType foodType = foodTypeDao.getFoodType(typeID);
        try {
            String newPath = MyFile.saveFile(pic);
            foodType.setPicture(newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        foodType.setName(name);

        return foodTypeDao.updateFoodType(foodType);
    }

    public void setFoodDao(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public void setFoodTypeDao(FoodTypeDao foodTypeDao) {
        this.foodTypeDao = foodTypeDao;
    }

    public void setFoodExtraDao(FoodExtraDao foodExtraDao) {
        this.foodExtraDao = foodExtraDao;
    }
}
