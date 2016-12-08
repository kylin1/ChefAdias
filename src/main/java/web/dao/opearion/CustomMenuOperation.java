package web.dao.opearion;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.model.po.CustomMenuFood;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public interface CustomMenuOperation {

    @Select({"select * from `custom_menu_food` where id=#{mmenu_foodid}"})
    CustomMenuFood get(int mmenu_foodid);

    @Insert({"insert into `custom_menu_food` ( `type`, `name`, `price`) " +
            "values ( #{type}, #{name}, #{price})"})
    void add(CustomMenuFood customMenuFood);

    @Update({"update `custom_menu_food` set `type`=#{type}, `name`=#{name}, `price`=#{price} " +
            " where `id`=#{id} "})
    void update(CustomMenuFood customMenuFood);

    @Delete({"delete from `custom_menu_food` where id=#{mmenu_foodid}"})
    void delete(int mmenu_foodid);
}
