package biz;

import org.junit.Test;
import web.biz.impl.OrderImpl;
import web.dao.impl.*;
import web.model.vo.AddOrderVO;
import web.model.vo.OrderItemVO;
import web.model.vo.UserOrderVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 09/12/2016.
 * All rights reserved.
 */
public class OrderTest {

    private OrderImpl service = new OrderImpl();

    public OrderTest() {
        service.setOrderItemDao(new OrderItemDaoImpl());
        service.setOrderDao(new OrderDaoImpl());
        service.setFoodDao(new FoodDaoImpl());
        service.setCustomMenuFoodDao(new CustomMenuFoodDaoImpl());
        service.setCustomMenuListDao(new CustomMenuListDaoImpl());
        service.setCustomOrderDao(new CustomOrderDaoImpl());
    }

    //    @Test
    public void testAdd() {
        AddOrderVO addOrderVO = new AddOrderVO();
        addOrderVO.setBowl_info(1);
        addOrderVO.setTime("2015-12-02 12:12:12");
        addOrderVO.setPay_type(1);
        addOrderVO.setPrice(new BigDecimal("22"));
        addOrderVO.setTicket_info(1);
        List<OrderItemVO> itemVOList = new ArrayList<>();
        OrderItemVO item = new OrderItemVO();
        item.setFoodid("1");
        item.setNum(1);
        itemVOList.add(item);

        OrderItemVO item2 = new OrderItemVO();
        item2.setFoodid("2");
        item2.setNum(2);
        itemVOList.add(item2);

        addOrderVO.setFood_list(itemVOList);
        addOrderVO.setUserid(1);
        service.addOrder(addOrderVO);
    }

    //    @Test
    public void testGet() {
        UserOrderVO vo = service.getOrder(14);
        System.out.println(vo.getPrice());
    }

    //    @Test
    public void testAddMOrder() {
        service.addMOrder(1, 1, 1);
    }
}
