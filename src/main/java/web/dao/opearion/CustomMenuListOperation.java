package web.dao.opearion;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.model.po.CustomMenuList;

import java.util.List;

/**
 * Created by kylin on 12/12/2016.
 * All rights reserved.
 */
public interface CustomMenuListOperation {

    @Insert({"insert into `custom_menu_list` ( `number`, `menu_id`, `food_id`) " +
            "values ( #{number}, #{menu_id}, #{food_id})"})
    void insert(CustomMenuList list);

    @Select({"select * from `custom_menu_list` where `menu_id`=#{menu_id} "})
    List<CustomMenuList> getMenuListOfMenu(int menuId);

    @Delete({"delete from `custom_menu_list` where `id`=#{id} "})
    void delete(int id);

    @Update({"update `custom_menu_list` set `number`=#{number}, `menu_id`=#{menu_id}, `food_id`=#{food_id} " +
            " where `id`=#{id} "})
    void update(CustomMenuList list);
}
