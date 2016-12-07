package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.TicketService;
import web.dao.OrderDao;
import web.dao.TicketDao;
import web.dao.UserTicketDao;
import web.model.po.Order;
import web.model.po.UserTicket;
import web.model.vo.TickInfoVO;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
@Service
public class TicketImpl implements TicketService {
    private static final double UPPER_BOUND = 80;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserTicketDao userTicketDao;

    @Override
    public TickInfoVO getTicketInfo(int userId) {
        //UserTicketDAO
        List<UserTicket> ticketList = userTicketDao.getUserTicket(userId);
        //list size为1
        UserTicket ticket = ticketList.get(0);
        Date expireTime = ticket.getExpire_time();
        int tickID = ticket.getTicket_id();

        //TicketDAO


        //OrderDAO
        List<Order> orderList = orderDao.getOrderOfUser(userId);
        BigDecimal dailySum = new BigDecimal(0);
        for (Order order : orderList) {
            Date createTime = order.getCreate_time();
            if (createTime.compareTo(expireTime) == 0) {
                dailySum = dailySum.add(order.getPrice());
            }
        }
        return null;
    }

    @Override
    public MyMessage buyTicket(int userId, int ticketId) {
        UserTicket userTicket = new UserTicket();
        userTicket.setUser_id(userId);
        userTicket.setTicket_id(ticketId);

        return null;
    }
}
