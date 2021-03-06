package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.FoodTypeDao;
import web.dao.opearion.FoodOperation;
import web.dao.opearion.FoodTypeOperation;
import web.dao.util.MybatisUtils;
import web.model.po.FoodType;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
@Repository("foodTypeDao")
@Transactional
public class FoodTypeDaoImpl implements FoodTypeDao {

    SqlSession session;
    FoodTypeOperation operation;
    FoodOperation foodOperation;

    @Override
    public List<FoodType> getAllFoodType() {
        List<FoodType> list = new ArrayList<>();

        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodTypeOperation.class);
            list = this.operation.getDishMenu();
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            this.session.close();
        }
//
//        for (FoodType foodType:list){
//            int id = foodType.getId();
//            int num = this.operation.getDishNumInType(id);
//            foodType.setFoodNum(num);
//        }
        this.session.close();

        return list;
    }

    @Override
    public FoodType getFoodType(int id) {
        FoodType foodType = null;

        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodTypeOperation.class);
            this.foodOperation = this.session.getMapper(FoodOperation.class);

            // 基本分类信息
            foodType = this.operation.getFoodType(id);
            // 如果分类不存在,直接返回null
            if(foodType == null){
                return null;
            }

//            // 分类里面的菜品数目
//            int num = this.operation.getDishNumInType(id);
//            foodType.setFoodNum(num);
//
//            // 分类里面的菜品详细信息
//            List<Food> listInType = this.foodOperation.getDishOfType(id);
//            foodType.setFoodList(listInType);

            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            this.session.close();
        }

        this.session.close();

        return foodType;
    }

    @Override
    public MyMessage addFoodType(FoodType newFoodType) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodTypeOperation.class);
            this.operation.save(newFoodType);
            this.session.commit();
            myMessage =  new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false, 0, ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }

    @Override
    public MyMessage deleteFoodType(int id) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodTypeOperation.class);
            this.operation.delete(id);
            this.session.commit();
            myMessage =  new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false, 0, ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }

    @Override
    public MyMessage updateFoodType(FoodType foodType) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodTypeOperation.class);
            this.operation.update(foodType);
            this.session.commit();
            myMessage =  new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false, 0, ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }
}
