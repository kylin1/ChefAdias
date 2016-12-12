package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.DishService;
import web.dao.FoodDao;
import web.dao.FoodExtraDao;
import web.dao.FoodTypeDao;
import web.model.exceptions.ErrorCode;
import web.model.exceptions.NotFoundException;
import web.model.po.Food;
import web.model.po.FoodExtra;
import web.model.po.FoodType;
import web.model.vo.FoodTypeBasicVO;
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


    @Autowired
    private FoodExtraDao foodExtraDao;

    @Override
    public List<FoodVO> getAllDish() {
        //获取PO
        List<Food> foods = foodDao.getAllFood();
        //转换为VO
        return this.poToVo(foods);
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
        //获取VO
        List<FoodVO> foodInfoList = this.poToVo(foodList);
        return new FoodTypeVO(foodType.getPicture(), foodType.getName(), foodList.size(), foodInfoList);
    }

    /**
     * 获取一个食物附属的其他食物
     *
     * @param mainFood 主要食物
     * @return 附属于主要食物的额外食物
     */
    private List<FoodVO> getExtraFoodList(Food mainFood) {
        List<FoodVO> resultList = new ArrayList<>();

        //获取主要食物的附加食物信息
        int mainId = mainFood.getId();
        List<FoodExtra> extraList = this.foodExtraDao.getExtraOfMainFood(mainId);

        //对于一个食物包含的额外食物
        for (FoodExtra extra : extraList){
            int extraId = extra.getExtra_food_id();
            Food food = foodDao.getFood(extraId);
            FoodVO extraFoodVO = new FoodVO(food.getId() + "", food.getName(), food.getPicture(),
                    food.getPrice(), food.getLike(), food.getDislike(),null);
            resultList.add(extraFoodVO);
        }

        return resultList;
    }

    /**
     * 数据层数据转换为VO
     * 添加每一个VO对应的额外食物信息
     *
     * @param foodList
     * @return
     */
    private List<FoodVO> poToVo(List<Food> foodList) {
        List<FoodVO> foodInfoList = new ArrayList<>();
        for (Food food : foodList) {
            //对于每一个PO需要获取它额外的附加食物信息
            FoodVO foodVO = new FoodVO(food.getId() + "", food.getName(), food.getPicture(),
                    food.getPrice(), food.getLike(), food.getDislike(), this.getExtraFoodList(food));
            foodInfoList.add(foodVO);
        }
        return foodInfoList;
    }

    public void setFoodExtraDao(FoodExtraDao foodExtraDao) {
        this.foodExtraDao = foodExtraDao;
    }

    public void setFoodDao(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public void setFoodTypeDao(FoodTypeDao foodTypeDao) {
        this.foodTypeDao = foodTypeDao;
    }
}
