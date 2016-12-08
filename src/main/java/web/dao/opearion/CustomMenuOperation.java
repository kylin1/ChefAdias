package web.dao.opearion;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import web.model.po.CustomMenu;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public interface CustomMenuOperation {

    @Insert({"insert into `custom_menu` ( `meat_id`, `flavor_id`, `meal_num`, `meal_id`, `user_id`, `sauce_id`," +
            " `snack_num`, `vegetable_id`, `meat_num`, `vegetable_num`, `snack_id`, `name`, `sauce_num`) " +
            " values ( #{meat_id}, #{flavor_id}, #{meal_num}, #{meal_id}, #{user_id}, #{sauce_id}, " +
            " #{snack_num}, #{vegetable_id}, #{meat_num}, #{vegetable_num}, #{snack_id}, #{name}, #{sauce_num})"})
    void insert(CustomMenu customMenu);

    @Select({"select * from `custom_menu` where id = #{id}"})
    CustomMenu get(int id);

    @Delete({"delete from `custom_menu` where id = #{id} "})
    void delete(int id);

}
