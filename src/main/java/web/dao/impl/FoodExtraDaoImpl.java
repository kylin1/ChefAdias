package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import web.dao.FoodExtraDao;
import web.dao.opearion.FoodExtraOperation;
import web.dao.util.MybatisUtils;
import web.model.po.FoodExtra;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public class FoodExtraDaoImpl implements FoodExtraDao {

    SqlSession session;

    FoodExtraOperation operation;

    @Override
    public List<FoodExtra> getExtraOfMainFood(int mainFoodId) {
        List<FoodExtra>  list = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodExtraOperation.class);
            list = this.operation.getExtraFood(mainFoodId);
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
    public MyMessage addExtraFood(FoodExtra foodExtra) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodExtraOperation.class);
            this.operation.insert(foodExtra);
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
    public FoodExtra getFoodExtra(int id) {
        FoodExtra foodExtra = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodExtraOperation.class);
            foodExtra = this.operation.get(id);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return foodExtra;
    }

    @Override
    public MyMessage updateFoodExtra(FoodExtra foodExtra) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodExtraOperation.class);
            this.operation.update(foodExtra);
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
    public MyMessage deleteFoodExtra(int id) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(FoodExtraOperation.class);
            this.operation.delete(id);
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
}
