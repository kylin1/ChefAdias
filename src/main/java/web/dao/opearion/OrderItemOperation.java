package web.dao.opearion;

import org.apache.ibatis.annotations.Select;
import web.model.po.OrderItem;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public interface OrderItemOperation {

    @Select({"select list.order_id,list.food_id,f.`name` as foodName,f.price,list.food_num from `food_list` list,`food` f\n" +
            " where list.order_id = #{orderId} and f.id = list.food_id;"})
    List<OrderItem> getOrderItem(int orderId);

}
