package web.biz;

import web.model.po.Ticket;
import web.model.vo.TickInfoVO;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
public interface TicketService {

    /**
     * 获取商店所有可用的餐券
     *
     * @return
     */
    List<Ticket> getTicketList();

    /**
     * 获取个人餐券信息
     *
     * @param userId
     * @return
     */
    TickInfoVO getTicketInfo(int userId);

    /**
     * 购买餐券
     *
     * @param userId
     * @param ticketId
     * @return MyMessage
     */
    MyMessage buyTicket(int userId, int ticketId);
}
