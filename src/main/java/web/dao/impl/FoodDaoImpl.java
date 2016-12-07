package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.FoodDao;
import web.dao.opearion.FoodOperation;
import web.dao.util.MybatisUtils;
import web.model.po.Food;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
@Repository("foodDao")
@Transactional
public class FoodDaoImpl implements FoodDao {

    SqlSession session;
    FoodOperation operation;

    @Override
    public List<Food> getAllFood() {
        List<Food>  list = new ArrayList<>();

        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodOperation.class);
            list = this.operation.getAllFood();
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }

        return list;
    }

    @Override
    public List<Food> getFoodOfType(int foodType) {
        List<Food> result = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodOperation.class);
            result= this.operation.getDishOfType(foodType);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return result;
    }

    @Override
    public MyMessage addFood(Food newFood) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodOperation.class);
            this.operation.save(newFood);
            this.session.commit();
            myMessage = new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false,1,ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }

    @Override
    public MyMessage deleteFood(int id) {
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodOperation.class);
            this.operation.delete(id);
            this.session.commit();
            return new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            return new MyMessage(false,1,ex.getMessage());
        } finally {
            this.session.close();
        }
    }

    @Override
    public MyMessage updateFood(Food food) {
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodOperation.class);
            this.operation.update(food);
            this.session.commit();
            return new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            return new MyMessage(false,1,ex.getMessage());
        } finally {
            this.session.close();
        }
    }
}
