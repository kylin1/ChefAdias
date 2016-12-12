package web.biz.impl;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.biz.UserService;
import web.dao.UserDao;
import web.model.po.User;
import web.model.exceptions.*;
import web.model.vo.UserInfoVO;
import web.model.vo.UserVO;
import web.tools.MyFile;
import web.tools.MyMessage;

import java.io.IOException;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
@Service
public class UserImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserVO login(String email, String password) throws NotFoundException, WrongInputException {
        //根据邮箱信息查询用户
        User user = userDao.getUserByMail(email);
        //查询不到用户
        if (user == null) {
            throw new NotFoundException(ErrorCode.NO_USER);
        }
        //密码正确
        if (user.getPassword().equals(password)) {
            return new UserVO(user.getId() + "", user.getUsername(), user.getAvatar());
        } else {
            throw new WrongInputException(ErrorCode.WRONG_PASSWORD);
        }
    }

    @Nullable
    @Override
    public UserVO register(String email, String password, String username) throws DataConflictException, NotFoundException, WrongInputException {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUsername(username);
        MyMessage message = userDao.addUser(newUser);
        //注册成功
        if (message.isSuccess()) {
            return login(email, password);
        } else {
            return null;
        }
    }

    @Override
    public boolean changeAddress(int userId, String newAddress) throws NotFoundException {
        User user = getUser(userId);
        user.setAddress(newAddress);
        userDao.updateUser(user);
        return true;
    }

    @Override
    public boolean changePhone(int userId, String newPhone) throws NotFoundException {
        User user = getUser(userId);
        user.setPhone(newPhone);
        userDao.updateUser(user);
        return true;
    }

    @Override
    public boolean changeAvatar(int userId, MultipartFile newAvatar) throws NotFoundException, ServerException {
        try {
            User user = getUser(userId);
            //保存文件到服务器
            String localPath = MyFile.saveFile(newAvatar);
            //更新头像路径并保存
            user.setAvatar(localPath);
            userDao.updateUser(user);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerException(ErrorCode.SERVER);
        }
    }

    @Override
    public UserInfoVO getUserInfo(int userId) throws NotFoundException {
        User user = userDao.getUser(userId);
        if (user == null) {
            throw new NotFoundException(ErrorCode.NO_USER);
        } else {
            return new UserInfoVO(user.getPhone(), user.getAddress());
        }
    }

    /**
     * 获取user全部信息
     *
     * @param userId 用户ID
     * @return 用户model
     * @throws NotFoundException
     */
    private User getUser(int userId) throws NotFoundException {
        User user = userDao.getUser(userId);
        if (user == null) {
            throw new NotFoundException(ErrorCode.NO_USER);
        } else {
            return user;
        }
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
