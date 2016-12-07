package web.dao.opearion;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.model.po.Order;

import java.util.List;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public interface OrderOperation {

    @Insert({"insert into `order` ( `is_finish`, `ticket_info`, `price`, `pay_type`, " +
            "`bowl_info`, `user_id`, `create_time`) " +
            "values (#{is_finish},#{ticket_info},#{price},#{pay_type}," +
            "#{bowl_info},#{user_id},#{create_time},)"})
    void save(Order order);

    @Select({"select * from `order` where user_id = #{user_id}"})
    List<Order> getOrderOfUser(int userID);

    @Select({"select * from `order` where id = #{id}"})
    Order getOrder(int id);

    @Update({"update `order` set `is_finish`=#{state} where `id`=#{orderId} "})
    void updateState(int orderId, int state);
}
