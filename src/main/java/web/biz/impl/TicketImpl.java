package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.TicketService;
import web.dao.OrderDao;
import web.dao.TicketDao;
import web.dao.UserTicketDao;
import web.model.po.Order;
import web.model.po.Ticket;
import web.model.po.UserTicket;
import web.model.vo.TickInfoVO;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public List<Ticket> getTicketList() {
        return this.ticketDao.getAllTicket();
    }

    @Override
    public TickInfoVO getTicketInfo(int userId) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                sdf = new SimpleDateFormat("yyyy-MM-dd");
        //UserTicketDAO
        List<UserTicket> ticketList = userTicketDao.getUserTicket(userId);
        if (ticketList == null || ticketList.size() == 0) {
            return null;
        }
        //list size为1
        UserTicket userTicket = ticketList.get(0);
        Date expireTime = userTicket.getExpire_time();
        String expire_time = sdf.format(expireTime);
        int tickID = userTicket.getTicket_id();

        //TicketDAO
        Ticket ticket = ticketDao.getTicket(tickID);
        BigDecimal dailyUpper = ticket.getDaily_upper();

        //OrderDAO
        Date today = new Date();
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, 1);
        Date tomorrow = ca.getTime();
        List<Order> orderList = orderDao.getOrderInDay(userId, format.format(today), format.format(tomorrow));
        BigDecimal dailySum = new BigDecimal(0);
        for (Order order : orderList) {
            dailySum = dailySum.add(order.getPrice());
        }
        return new TickInfoVO(dailyUpper.subtract(dailySum), expire_time);
    }

    @Override
    public MyMessage buyTicket(int userId, int ticketId) {
        UserTicket userTicket = new UserTicket();
        userTicket.setUser_id(userId);
        userTicket.setTicket_id(ticketId);

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, 30);
        Date nextMonthDate = ca.getTime();
        userTicket.setExpire_time(nextMonthDate);

        return userTicketDao.addUserTicket(userTicket);
    }
}
