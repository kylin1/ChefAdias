package web.biz;

import web.model.po.UserTicket;
import web.tools.MyMessage;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
public interface TicketService {

    /**
     * 获取个人餐券信息
     *
     * @param userId
     * @return
     */
    UserTicket getTicketInfo(int userId);

    /**
     * 购买餐券
     *
     * @param userId
     * @param ticketId
     * @return MyMessage
     */
    MyMessage buyTicket(int userId, int ticketId);
}
