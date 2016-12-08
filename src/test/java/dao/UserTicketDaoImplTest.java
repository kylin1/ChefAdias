package dao;

import org.junit.Test;
import web.dao.impl.UserTicketDaoImpl;
import web.model.po.UserTicket;
import web.tools.CheckClass;

import java.util.Date;
import java.util.List;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public class UserTicketDaoImplTest {

//    @Test
    public void test() throws ClassNotFoundException {
        UserTicketDaoImpl dao = new UserTicketDaoImpl();
        List<UserTicket> list =dao.getUserTicket(1);
        for (UserTicket ticket:list){
            CheckClass.checkObject("UserTicket",ticket);
        }
    }

    @Test
    public void test2() throws ClassNotFoundException {
        UserTicketDaoImpl dao = new UserTicketDaoImpl();
        UserTicket ticket = new UserTicket();
        ticket.setUser_id(2);
        ticket.setExpire_time(new Date());
        ticket.setTicket_id(1);
        dao.addUserTicket(ticket);
    }


}
