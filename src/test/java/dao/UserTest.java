package dao;

import web.dao.UserDao;
import web.dao.impl.UserDaoImpl;
import web.model.User;
import web.model.exceptions.DataConflictException;
import web.model.exceptions.NotFoundException;
import web.tools.CheckClass;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class UserTest {

    UserDao dao = new UserDaoImpl();

//    @Test
    public void testInsert() throws DataConflictException {
        User user = new User();
        user.setUsername("kuixi");
        user.setPassword("123123");
        user.setEmail("18795853969@163.com");
        user.setAddress("NJU gulou campus");
        user.setAvatar("path to pic");
        user.setPhone("18795853969");
        dao.addUser(user);
    }

//    @Test
    public void testGet() throws NotFoundException {
        List<User> userList = dao.getAllUser();
//        List<User> userList = dao.searchUser("xi");
        this.print(userList);
    }

//    @Test
    public void oneUser(){
//        User user = dao.getUser(1);
        User user = null;
        user = dao.getUserByMail("187958539691231232@163.com");
        try {
            CheckClass.checkObject("User",user);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


//    @Test
    public void update() throws NotFoundException, ClassNotFoundException {
        int id = 1;
        User user = dao.getUser(id);
        user.setUsername("王梦麟666");
        user.setPassword("123123");
        user.setEmail("18795853969@163.com");
        user.setAddress("NJU gulou campus");
        user.setAvatar("path to pic");
        user.setPhone("18795853969");
        dao.updateUser(user);
        user = dao.getUser(id);
        CheckClass.checkObject("User",user);
    }

    private void print(List<User> userList){
        for (User user:userList){
            try {
                CheckClass.checkObject("User",user);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}

