package web.dao.opearion;

import org.apache.ibatis.annotations.Select;
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

}
