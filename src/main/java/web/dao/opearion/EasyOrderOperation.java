package web.dao.opearion;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.model.EasyOrder;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public interface EasyOrderOperation {

    @Select({"select easy.order_id, easy.user_id, o.price, o.create_time as `time`\n" +
            "from `easy_order` easy,`order` o \n" +
            "where easy.user_id = #{userId} and o.id = easy.id;"})
    EasyOrder getEasyOrder(int userId);

    @Select({"select * from `easy_order` where user_id = #{userId}"})
    EasyOrder getEasyOrderBasic(int userId);

    @Insert({"insert into `easy_order` ( `order_id`, `user_id`) " +
            " values (#{order_id}, #{user_id})"})
    void insert(EasyOrder easyOrder);

    @Select({"select * from `easy_order` where id = #{id}"})
    EasyOrder getEasyOrderOfId(int id);

    @Update({"update `easy_order` set `order_id`=#{order_id} " +
            "where `user_id`=#{user_id} "})
    void update(EasyOrder easyOrder);
}
