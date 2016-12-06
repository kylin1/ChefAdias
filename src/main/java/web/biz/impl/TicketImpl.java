package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.TicketService;
import web.dao.TicketDao;
import web.model.Ticket;
import web.model.UserTicket;

import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
@Service
public class TicketImpl implements TicketService {
    @Autowired
    TicketDao dao;

    @Override
    public UserTicket getTicketInfo(int userId) {
        List<Ticket> ticketList = dao.getTicketOfUser(userId);
        
        return null;
    }

    @Override
    public boolean buyTicket(int userId, int ticketId) {
        return false;
    }
}
