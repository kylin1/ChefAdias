package dao;

import web.dao.TicketDao;
import web.dao.impl.TicketDaoImpl;
import web.model.po.Ticket;
import web.tools.MyResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public class TicketDaoTest {

    private TicketDao dao = new TicketDaoImpl();

//    @Test
    public void get() throws ClassNotFoundException {
        List<Ticket> list = dao.getAllTicket();
        String result = MyResponse.success(list);
        System.out.printf(result);
//        for (Ticket ticket:list){
//            CheckClass.checkObject("Ticket",ticket);
//        }
    }

//    @Test
    public void add(){
        Ticket ticket = new Ticket();
        ticket.setName("monthly ticket");
        ticket.setDaily_upper(new BigDecimal("50.8"));
        ticket.setDescription("ticket info");
        ticket.setExpire_day(31);
        dao.addTicket(ticket);
    }

//    @Test
    public void update(){
        Ticket ticket = dao.getTicket(1);
        ticket.setName("monthly ticket");
        ticket.setDaily_upper(new BigDecimal("50.8"));
        ticket.setDescription("ticket info");
        ticket.setExpire_day(31);
        dao.updateTicket(ticket);
    }

//    @Test
    public void delete(){
        dao.deleteTicket(5);
    }
}
