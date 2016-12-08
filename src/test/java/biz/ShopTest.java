package biz;

import org.junit.Test;
import web.biz.ShopOrderService;
import web.biz.impl.ShopOrderImpl;
import web.model.po.OrderItem;
import web.model.vo.ShopOrderItemVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Alan on 2016/12/7.
 */
public class ShopTest {
    @Test
    public void testUserList() {

    }

    @Test
    public void testGetOrderList() {
//        ShopOrderService shopOrder = new ShopOrderImpl();
//        List<ShopOrderItemVO> orderList = shopOrder.getOrderList("20161208");
//        System.out.println(orderList);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"),
//                sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//        Date today;
//        try {
//            today = sdf.parse("20161208");
//            Calendar ca = Calendar.getInstance();
//            ca.setTime(today);
//            ca.add(Calendar.DATE, 1);
//            Date tomorrow = ca.getTime();
//
//            String newToday = sdf2.format(today);
//            String newTommorow = sdf2.format(tomorrow);
//            System.out.println(newToday);
//            System.out.println(newTommorow);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }
}
