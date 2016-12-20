package biz;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import web.biz.impl.TicketImpl;
import web.dao.impl.OrderDaoImpl;
import web.dao.impl.TicketDaoImpl;
import web.dao.impl.UserTicketDaoImpl;

/**
 * Created by Alan on 2016/12/20.
 */
public class TicketTest {
    private TicketImpl ticketImpl = new TicketImpl();

    //    @Before
    public void init() {
        ticketImpl.setOrderDao(new OrderDaoImpl());
        ticketImpl.setTicketDao(new TicketDaoImpl());
        ticketImpl.setUserTicketDao(new UserTicketDaoImpl());
    }

    //    @Test
    public void testGetTicketInfo() {
        Gson gson = new Gson();
        System.out.println(gson.toJson(ticketImpl.getTicketInfo(1)));
    }
}
