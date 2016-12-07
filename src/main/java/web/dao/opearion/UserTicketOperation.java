package web.dao.opearion;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import web.model.po.UserTicket;

import java.util.List;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public interface UserTicketOperation {


    /**
     * 得到用户购买的餐券,过期日期
     *
     * @param userId
     * @return
     */
    @Select({"select * from `user_ticket` where `user_id` = #{userId}"})
    List<UserTicket> getUserTicket(int userId);

    /**
     * 购买餐券
     *
     * @param userTicket
     */
    @Insert({"insert into `user_ticket` (`expire_time`, `user_id`, `ticket_id`) " +
            "values (#{expire_time},#{user_id},#{ticket_id})"})
    void insertUserTicket(UserTicket userTicket);


}
