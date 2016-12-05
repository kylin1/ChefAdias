package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.dao.opearion.UserOperation;
import web.dao.util.MybatisUtils;
import web.model.User;
import web.model.exceptions.DataConflictException;
import web.model.exceptions.ErrorCode;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

    SqlSession session;
    UserOperation operation;

    @Override
    public List<User> getAllUser() {

        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(UserOperation.class);
            List<User> list = this.operation.getAllUser();
            this.session.commit();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            return new ArrayList<>();
        } finally {
            this.session.close();
        }

    }

    @Override
    public List<User> searchUser(String userName) {
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(UserOperation.class);
            String input = "%" + userName + "%";
            List<User> list = this.operation.searchUser(input);
            this.session.commit();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            return new ArrayList<>();
        } finally {
            this.session.close();
        }
    }

    @Override
    public User getUser(int id){
        User user = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(UserOperation.class);
            user = this.operation.getUser(id);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return user;
    }

    @Override
    public User getUserByMail(String email){
        User user = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(UserOperation.class);
            user = this.operation.getUserByMail(email);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return user;
    }

    @Override
    public MyMessage addUser(User user) throws DataConflictException {
        String email = user.getEmail();
        MyMessage myMessage = null;

        //邮箱被占用
        if (this.isEmailTaken(email)) {
            throw new DataConflictException(ErrorCode.EMAIL_TAKEN);
        }

        //没有被占用
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(UserOperation.class);
            this.operation.save(user);
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
    public MyMessage updateUser(User user) {
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(UserOperation.class);
            this.operation.update(user);
            this.session.commit();
            return new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            return new MyMessage(false, 0, ex.getMessage());
        } finally {
            this.session.close();
        }
    }

    private boolean isEmailTaken(String email) {
        User user = this.getUserByMail(email);
        return user != null;
    }
}
