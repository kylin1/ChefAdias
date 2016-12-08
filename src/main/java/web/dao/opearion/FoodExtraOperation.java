package web.dao.opearion;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.model.po.FoodExtra;

import java.util.List;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public interface FoodExtraOperation {

    @Select({"select * from `food_extra` where `food_id` = #{mainFoodId} "})
    List<FoodExtra> getExtraFood(int mainFoodId);

    @Insert({"insert into `food_extra` ( `food_id`, `extra_food_id`) values (#{food_id}, #{extra_food_id}) "})
    void insert(FoodExtra foodExtra);

    @Select({"select * from `food_extra` where `id` = #{id} "})
    FoodExtra get(int id);

    @Update({"update `food_extra` set `extra_food_id`=#{extra_food_id},`food_id`=#{food_id} " +
            " where `id`=#{id} "})
    void update(FoodExtra foodExtra);

    @Delete({"delete from `food_extra` where `id` = #{id}"})
    void delete(int id);
}
