package web.dao.opearion;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import web.model.Food;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface FoodOperation {

    @Select({"select * from food"})
    List<Food> getAllFood();

    //like 是关键字,坑!
    @Insert({"insert into food(name,picture,price,type_id,`description`,`like`,dislike)" +
            " values(#{name},#{picture},#{price},#{type_id},#{description},#{like},#{dislike})"})
    void save(Food food);

    @Select({"select * from food where type_id =#{type}"})
    List<Food> getDishOfType(int type);
}
