package web.dao.opearion;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.model.po.Ticket;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public interface TicketOperation {

    @Select({"select * from `ticket` "})
    List<Ticket> getAllTicket();

    @Select({"select * from `ticket` where `id` = #{id}"})
    Ticket getTicket(int id);

    @Insert({"insert into `ticket` ( `expire_day`, `daily_upper`, `name`, `description`) " +
            "values ( #{expire_day}, #{daily_upper}, #{name}, #{description} )"})
    void insertTicket(Ticket ticket);

    @Update({"update `ticket` set `expire_day`=#{expire_day}, `daily_upper`=#{daily_upper}, " +
            "`name`=#{name}, `description`=#{description} where `id`=#{id} "})
    void update(Ticket ticket);

    @Delete({"delete from `ticket` where `id`=#{id}"})
    void delete(int id);
}
