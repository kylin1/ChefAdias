package web.dao.opearion;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import web.model.po.CustomMenu;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public interface CustomMenuOperation {

    @Insert({"insert into `custom_menu`"})
    @Options(useGeneratedKeys = true)
    void insert(CustomMenu customMenu);

    @Select({"select * from `custom_menu` where id = #{id}"})
    CustomMenu get(int id);

    @Delete({"delete from `custom_menu` where id = #{id} "})
    void delete(int id);

}
