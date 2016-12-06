package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.biz.UserService;
import web.dao.UserDao;
import web.model.User;
import web.model.exceptions.*;
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
    public User login(String email, String password) throws NotFoundException, WrongInputException {
        //根据邮箱信息查询用户
        System.out.println("bl:email="+email+"pas="+password);
        User user = this.userDao.getUserByMail(email);

        if(user == null){
            throw new NotFoundException(ErrorCode.NO_USER);
        }
        System.out.println(user.getId());

        //密码正确
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new WrongInputException(ErrorCode.WRONG_PASSWORD);
        }
    }

    @Override
    public User register(String email, String password, String username) throws DataConflictException {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUsername(username);
        MyMessage message = this.userDao.addUser(newUser);
        //注册成功
        if (message.isSuccess()) {
            return this.userDao.getUserByMail(email);
        }
        return newUser;
    }

    @Override
    public boolean changeAddress(int userId, String newAddress) throws NotFoundException {
        User user = this.getUser(userId);
        user.setAddress(newAddress);
        this.userDao.updateUser(user);
        return true;
    }

    @Override
    public boolean changePhone(int userId, String newPhone) throws NotFoundException {
        User user = this.getUser(userId);
        user.setPhone(newPhone);
        this.userDao.updateUser(user);
        return true;
    }

    @Override
    public boolean changeAvatar(int userId, MultipartFile newAvatar) throws NotFoundException, ServerException {
        try {
            User user = this.getUser(userId);
            //保存文件到服务器
            String newPath = MyFile.saveFile(newAvatar);
            //更新头像路径并保存
            user.setAvatar(newPath);
            this.userDao.updateUser(user);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerException(ErrorCode.SERVER);
        }
    }

    @Override
    public User getUser(int userId) throws NotFoundException {
        User user = this.userDao.getUser(userId);
        if(user == null){
            throw new NotFoundException(ErrorCode.NO_USER);
        }
        return user;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
