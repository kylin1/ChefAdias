package web.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserTicketDao;
import web.model.po.Ticket;
import web.model.po.UserTicket;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("userTicketDao")
public class UserTicketDaoImpl implements UserTicketDao{

    @Override
    public MyMessage addUserTicket(UserTicket userTicket) {
        return null;
    }

    @Override
    public List<Ticket> getUserTicket(int userId) {
        return null;
    }
}
