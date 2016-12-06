package Util;

import org.junit.Test;
import web.model.Bowl;
import web.model.Order;
import web.tools.CheckClass;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class TestCheck {

    @Test
    public void test() throws IllegalAccessException, ClassNotFoundException {
        Bowl bowl = new Bowl();
        bowl.setIs_return(1);
        bowl.setUser_id(1);
        CheckClass.checkObject("Bowl", bowl);
    }

    @Test
    public void testOrder() throws IllegalAccessException, ClassNotFoundException {
        Order order = new Order();
        order.setUser_id(123);
        order.setCreate_time(new Date());
        order.setPrice(new BigDecimal("123.23"));

        CheckClass.checkObject("Order", order);
    }
}
