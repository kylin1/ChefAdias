package web.dao;

<<<<<<< HEAD
import web.model.po.Ticket;
import web.model.po.UserTicket;
=======
import web.model.UserTicket;
>>>>>>> origin/master
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface UserTicketDao {

    /**
     * 用户购买餐券,增加用户-餐券信息
     *
     * @param userTicket
     * @return
     */
    MyMessage addUserTicket(UserTicket userTicket);

    /**
     * 获取用户购买的所有餐券
     *
     * @param userId
     * @return
     */
    List<UserTicket> getUserTicket(int userId);
}
