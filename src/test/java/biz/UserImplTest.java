package biz;

import web.biz.impl.UserImpl;
import web.dao.UserDao;
import web.dao.impl.UserDaoImpl;
import web.model.exceptions.NotFoundException;
import web.model.exceptions.WrongInputException;
import web.model.vo.UserVO;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class UserImplTest {

    private UserDao dao;

    private UserImpl service;

    public UserImplTest() {
        this.dao = new UserDaoImpl();
        this.service = new UserImpl();
        this.service.setUserDao(dao);
    }

//    @Test
    public void login() {
        try {
            UserVO user = this.service.login("888@163.com", "123123");
            System.out.println(user.getUserid());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (WrongInputException e) {
            e.printStackTrace();
        }
    }
//
//    @Test
//    public void register() {
//        try {
//            User user = this.service.register("1111@163.com", "123123", "Kylin666");
//            System.out.println(user.getId());
//        } catch (DataConflictException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
    public void testChangeString() {
        try {
            this.service.changeAddress(5, "1123123 addr");
            this.service.changePhone(5, "new phone");
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
