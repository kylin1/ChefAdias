package web.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.TicketDao;
import web.model.Ticket;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("ticketDao")
public class TicketDaoImpl implements TicketDao {

    @Override
    public List<Ticket> getTicketOfUser(int userId) {
        return null;
    }

    @Override
    public List<Ticket> getAllTicket() {
        return null;
    }

    @Override
    public MyMessage addTicket(Ticket newTicket) {
        return null;
    }

    @Override
    public MyMessage updateTicket(Ticket ticket) {
        return null;
    }

    @Override
    public MyMessage deleteTicket(int id) {
        return null;
    }
}
