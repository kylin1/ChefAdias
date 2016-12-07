package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.BowlDao;
import web.dao.opearion.BowlOperation;
import web.dao.util.MybatisUtils;
import web.model.po.Bowl;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("bowlDao")

public class BowlDaoImpl implements BowlDao {
    
    SqlSession session;
    BowlOperation operation;

    @Override
    public List<Bowl> getBowlOfUser(int userId) {
        List<Bowl> list = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(BowlOperation.class);
            list = this.operation.getBowlOfUser(userId);
            this.session.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            this.session.rollback();
        }finally {
            this.session.close();
        }
        return list;
    }

    @Override
    public MyMessage updateBowl(Bowl bowl) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(BowlOperation.class);
            this.operation.update(bowl);
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
    public MyMessage addBowl(Bowl bowl) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(BowlOperation.class);
            this.operation.insert(bowl);
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
