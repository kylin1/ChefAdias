package web.dao.opearion;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
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
            " values (#{is_finish},#{ticket_info},#{price},#{pay_type}," +
            "#{bowl_info},#{user_id},#{create_time})"})
    void save(Order order);

    @Select({"select * from `order` where user_id = #{userID}"})
    List<Order> getOrderOfUser(int userID);

    @Select({"select * from `order` o " +
            "where o.`create_time` >= #{startDate} and o.`create_time` < #{endDate}"})
    List<Order> getOrderInDay(@Param("startDate")String startDate, @Param("endDate")String endDate);

    @Select({"select * from `order` where id = #{id}"})
    Order getOrder(int id);

    @Update({"update `order` set `is_finish`=#{state} where `id`=#{orderId} "})
    void updateState(@Param("state")int state, @Param("orderId")int orderId);

    @Select({"select * from `order` o " +
            "where o.`user_id` = #{userId} and o.`create_time` >= #{startDate} " +
            "   and o.`create_time` < #{endDate}"})
    List<Order> getOrderOfUserInDay(@Param("userId")int userId,
                                    @Param("startDate")String startDate, @Param("endDate")String endDate);
}
