package web.dao.opearion;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.model.po.Bowl;

import java.util.List;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public interface BowlOperation {

    @Select({"select * from `bowl` where `user_id` = #{userId}"})
    List<Bowl> getBowlOfUser(int userId);

    @Insert({"insert into `Chef`.`bowl` ( `user_id`, `is_return`) values ( #{user_id}, #{is_return})"})
    void insert(Bowl bowl);

    @Update({"update `Chef`.`bowl` set `is_return`=#{is_return} where `user_id`=#{user_id} "})
    void update(Bowl bowl);
}
