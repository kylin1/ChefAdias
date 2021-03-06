package web.dao.opearion;

import org.apache.ibatis.annotations.*;
import web.model.po.Food;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface FoodOperation {

    @Select({"select * from food"})
    List<Food> getAllFood();

    @Select({"select * from food where id = #{id}"})
    Food getFood(int id);

    @Select({"select * from food where type_id =#{type}"})
    List<Food> getDishOfType(int type);

    //like 是关键字,坑!
    @Insert({"insert into food(name,picture,price,type_id,`description`,`like`,dislike)" +
            " values(#{name},#{picture},#{price},#{type_id},#{description},#{like},#{dislike})"})
    @Options(useGeneratedKeys = true) //自动返回插入的ID
    void save(Food food);

    @Update({"update `food` set `type_id`=#{type_id}, `price`=#{price}, `picture`=#{picture}, " +
            "`description`=#{description},`like`=#{like}, `dislike`=#{dislike}, `name`=#{name} where `id`=#{id} "})
    void update(Food food);

    @Delete({"delete from `food` where `id`=#{id} "})
    void delete(int id);

}
