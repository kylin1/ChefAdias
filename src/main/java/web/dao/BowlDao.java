package web.dao;

import web.model.Bowl;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface BowlDao {

    /**
     * 得到用户的所有碗
     *
     * @param userId
     * @return
     */
    List<Bowl> getBowlOfUser(int userId);

    /**
     * 修改用户还碗情况
     *
     * @param bowl
     * @return
     */
    MyMessage updateBowl(Bowl bowl);

    /**
     * 增加一个碗
     *
     * @return
     */
    MyMessage addBowl(Bowl bowl);
}
