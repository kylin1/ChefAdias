package web.dao.opearion;

import web.model.UserTicket;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public interface TicketOperation {


    List<UserTicket> getUserTicket(int userId);

    void insert(UserTicket userTicket);
}
