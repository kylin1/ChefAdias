package web.dao.opearion;

import org.apache.ibatis.annotations.Select;
import web.model.EasyOrder;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public interface EasyOrderOperation {

    @Select({""})
    EasyOrder getEasyOrder(int userId);

}
