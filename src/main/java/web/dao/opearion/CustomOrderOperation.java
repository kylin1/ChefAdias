package web.dao.opearion;

import org.apache.ibatis.annotations.*;
import web.model.po.CustomOrder;

import java.util.List;

/**
 * Created by kylin on 17/12/2016.
 * All rights reserved.
 */
public interface CustomOrderOperation {
    
    @Insert({"insert into `custom_order` ( `is_finish`, `ticket_info`, `pay_type`, " +
            "`bowl_info`, `create_time`, `menu_id`) " +
            "values ( #{is_finish}, #{ticket_info}, #{pay_type}, " +
            "#{bowl_info}, #{create_time}, #{menu_id})"})
    @Options(useGeneratedKeys = true)
    void insert(CustomOrder order);

    @Select({"select * from `custom_order` where menu_id = #{menuId}"})
    List<CustomOrder> getOrderOfMenu(@Param("menuId")int menuId);

    @Select({"select * from `custom_order` o " +
            "where o.`create_time` >= #{startDate} and o.`create_time` < #{endDate}"})
    List<CustomOrder> getOrderInDay(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select({"select * from `custom_order` o " +
            "where o.`menu_id` = #{menuId} and o.`create_time` >= #{startDate} " +
            "   and o.`create_time` < #{endDate}"})
    List<CustomOrder> getOrderOfUserInDay(@Param("menuId")int menuId,
                                          @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select({"select * from `custom_order` where id = #{id}"})
    CustomOrder getOrder(int id);

    @Update({"update `custom_order` set `is_finish`=#{state} where `id`=#{orderId} "})
    void updateState(@Param("state")int state, @Param("orderId")int orderId);
}
