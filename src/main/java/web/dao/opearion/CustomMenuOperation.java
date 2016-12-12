package web.dao.opearion;

import org.apache.ibatis.annotations.*;
import web.model.po.CustomMenu;

import java.util.List;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public interface CustomMenuOperation {

    @Insert({"insert into `custom_menu` ( `sauce`, `user_id`, `name`, `time`, `flavor`) " +
            "values ( #{sauce}, #{user_id}, #{name}, #{time}, #{flavor})"})
    @Options(useGeneratedKeys = true)
    void insert(CustomMenu customMenu);

    @Select({"select * from `custom_menu` where id = #{id}"})
    CustomMenu get(int id);

    @Select({"select * from `custom_menu` where user_id = #{user_id}"})
    List<CustomMenu> getCustomMenuOfUser(int userId);

    @Delete({"delete from `custom_menu` where id = #{id} "})
    void delete(int id);

    @Update({"update `custom_menu` set `sauce`=#{sauce}, `user_id`=#{user_id}, " +
            " `name`=#{name}, `time`=#{time}, `flavor`=#{flavor} " +
            " where `id`=#{id} "})
    void update(CustomMenu customMenu);
}
