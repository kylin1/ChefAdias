package web.dao.opearion;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import web.model.po.OrderItem;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public interface OrderItemOperation {

    @Select({"select * from `food_list` where `order_id` = #{orderId} "})
    List<OrderItem> getOrderItem(int orderId);

    @Insert({"insert into `food_list` ( `order_id`, `food_id`, `food_num`) " +
            " values ( #{order_id}, #{food_id}, #{food_num})"})
    void insert(OrderItem orderItem);
}
