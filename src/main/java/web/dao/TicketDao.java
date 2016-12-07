package web.dao;

import web.model.po.Ticket;
import web.model.po.UserTicket;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface TicketDao {

    /**
     * 获取餐厅当前提供的所有餐券列表
     *
     * @return
     */
    List<Ticket> getAllTicket();

    /**
     * 根据ID获取餐券
     *
     * @param id
     * @return
     */
    Ticket getTicket(int id);

    //为餐厅提供的CURD方法
    MyMessage addTicket(UserTicket newTicket);

    MyMessage updateTicket(Ticket ticket);

    MyMessage deleteTicket(int id);


}
