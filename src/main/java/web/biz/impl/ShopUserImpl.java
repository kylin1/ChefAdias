package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.ShopUserService;
import web.dao.UserDao;
import web.model.po.User;
import web.model.vo.ShopUserVO;
import web.model.vo.UserVO;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by Alan on 2016/12/7.
 */
@Service
public class ShopUserImpl implements ShopUserService {
    @Autowired
    UserDao userDao;

    @Override
    public List<ShopUserVO> getUserList() {
        List<User> userList = userDao.getAllUser();

        return null;
    }

    @Override
    public List<UserVO> searchUser(String username) {
        return null;
    }

    @Override
    public MyMessage setBowl(int userID, int state) {
        return null;
    }
}
