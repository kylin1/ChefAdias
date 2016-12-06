package web.dao;

import web.model.po.User;
import web.model.exceptions.DataConflictException;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface UserDao {

    /**
     * 获取用户列表
     *
     * @return
     */
    List<User> getAllUser();

    /**
     * 根据用户名关键字查找用户
     *
     * @param userName
     * @return
     */
    List<User> searchUser(String userName);

    /**
     * 一个用户信息
     *
     * @param id
     * @return
     */
    User getUser(int id);

    /**
     * 验证密码是否正确
     *
     * @param email
     * @return
     */
    User getUserByMail(String email);

    /**
     * 注册
     *
     * @param user
     * @return
     */
    MyMessage addUser(User user) throws DataConflictException;

    /**
     * 更新信息
     *
     * @param user
     * @return
     */
    MyMessage updateUser(User user);


}
