package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserTicketDao;
import web.dao.opearion.TicketOperation;
import web.dao.util.MybatisUtils;
import web.model.UserTicket;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("userTicketDao")
public class UserTicketDaoImpl implements UserTicketDao{

    SqlSession sqlSession;

    TicketOperation operation;

    @Override
    public MyMessage addUserTicket(UserTicket userTicket) {
        try {
            this.sqlSession = MybatisUtils.getSession();
            this.operation = this.sqlSession.getMapper(TicketOperation.class);
            this.operation.insert(userTicket);
            this.sqlSession.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            this.sqlSession.rollback();
        }finally {
            this.sqlSession.close();
        }
        return new MyMessage(true);
    }

    @Override
    public List<UserTicket> getUserTicket(int userId) {
        List<UserTicket> list = new ArrayList<>();
        try {
            this.sqlSession = MybatisUtils.getSession();
            this.operation = this.sqlSession.getMapper(TicketOperation.class);
            list = this.operation.getUserTicket(userId);
            this.sqlSession.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            this.sqlSession.rollback();
        }finally {
            this.sqlSession.close();
        }
        return list;
    }


}
