package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.TicketDao;
<<<<<<< HEAD
import web.model.po.Ticket;
import web.model.po.UserTicket;
=======
import web.dao.opearion.TicketOperation;
import web.model.Ticket;
import web.model.UserTicket;
>>>>>>> origin/master
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("ticketDao")
public class TicketDaoImpl implements TicketDao {

    SqlSession session;
    TicketOperation operation;

    @Override
    //TODO 逻辑层应该计算剩余今日金额
    public List<Ticket> getTicketOfUser(int userId) {
        return null;
    }

    @Override
    public List<Ticket> getAllTicket() {
        return null;
    }

    @Override
    public MyMessage addTicket(UserTicket newTicket) {
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
