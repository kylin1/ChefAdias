package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.ShopUserService;
import web.dao.*;
import web.model.po.Bowl;
import web.model.po.Order;
import web.model.po.User;
import web.model.po.UserTicket;
import web.model.vo.ShopUserExtraVO;
import web.model.vo.ShopUserVO;
import web.model.vo.UserVO;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2016/12/7.
 */
@Service
public class ShopUserImpl implements ShopUserService {
    @Autowired
    UserDao userDao;
    @Autowired
    UserTicketDao userTicketDao;
    @Autowired
    BowlDao bowlDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public List<ShopUserVO> getUserList() {
        List<User> userList = userDao.getAllUser();
        List<ShopUserVO> shopUserVOList = new ArrayList<>();
        for (User user : userList) {
            int userID = user.getId();

            //UserTicketDAO
            List<UserTicket> tickets = userTicketDao.getUserTicket(userID);
            String expireTime = null;
            if (tickets.size() != 0) {
                UserTicket ticket = tickets.get(0);
                DateFormat format = new SimpleDateFormat("YYYYMMdd");
                expireTime = format.format(ticket.getExpire_time());
            }

            //BowlDAO
            List<Bowl> bowlList = bowlDao.getBowlOfUser(userID);
            int bowlCondition = 0;
            if (bowlList != null) {
                if (bowlList.size() != 0) {
                    Bowl bowl = bowlList.get(0);
                    bowlCondition = bowl.getIs_return();
                }
            }

            //OrderDAO
            List<Order> orderList = orderDao.getOrderOfUser(userID);
            BigDecimal sum = new BigDecimal(0);
            for (Order order : orderList) {
                sum = sum.add(order.getPrice());
            }

            ShopUserExtraVO shopUserExtraVO = new ShopUserExtraVO(bowlCondition, expireTime, sum, user.getAddress(), user.getPhone());
            ShopUserVO shopUserVO = new ShopUserVO(user.getId() + "", user.getUsername(), user.getAvatar(), shopUserExtraVO);
            shopUserVOList.add(shopUserVO);
        }
        return shopUserVOList;
    }

    @Override
    public List<UserVO> searchUser(String username) {
        List<User> userList = userDao.searchUser(username);
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList) {
            UserVO userVO = new UserVO(user.getId() + "", user.getUsername(), user.getAvatar());
            userVOList.add(userVO);
        }
        return userVOList;
    }

    @Override
    public MyMessage setBowl(int userID, int state) {
        List<Bowl> bowlList = bowlDao.getBowlOfUser(userID);
        Bowl bowl = bowlList.get(0);
        bowl.setIs_return(state);

        return bowlDao.updateBowl(bowl);
    }
}
