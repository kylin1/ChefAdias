package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.TicketService;
import web.dao.TicketDao;
import web.model.Ticket;
import web.model.UserTicket;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
@Service
public class TicketImpl implements TicketService {
    private static final double UPPER_BOUND = 80;
    @Autowired
    private TicketDao dao;


    @Override
    public UserTicket getTicketInfo(int userId) {
        List<Ticket> ticketList = dao.getTicketOfUser(userId);
        //list size为1
        Ticket ticket = ticketList.get(0);

        UserTicket userTicket = new UserTicket();
        userTicket.setRemain(ticket.getDaily_upper());
        userTicket.setExpireTime(ticket.getExpire_time());

        return userTicket;
    }

    @Override
    public MyMessage buyTicket(int userId, int ticketId) {
        UserTicket userTicket = new UserTicket();
        userTicket.setUser_id(userId);
        userTicket.setTicket_id(ticketId);
        userTicket.setRemain(new BigDecimal(UPPER_BOUND));
        userTicket.setBought_time(new Date());

        return dao.addTicket(userTicket);
    }
}
