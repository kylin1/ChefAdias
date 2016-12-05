package web.dao;

import web.model.Ticket;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface TicketDao {

    /**
     * 获取个人餐券信息
     *
     * @param userId 用户ID
     * @return
     */
    List<Ticket> getTicketOfUser(int userId);

    /**
     * 获取餐厅当前提供的所有餐券列表
     *
     * @return
     */
    List<Ticket> getAllTicket();

    //为餐厅提供的CURD方法
    MyMessage addTicket(Ticket newTicket);

    MyMessage updateTicket(Ticket ticket);

    MyMessage deleteTicket(int id);


}
