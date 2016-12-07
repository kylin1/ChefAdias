package web.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.BowlDao;
import web.model.po.Bowl;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("bowlDao")

public class BowlDaoImpl implements BowlDao {

    @Override
    public List<Bowl> getBowlOfUser(int userId) {
        return null;
    }

    @Override
    public MyMessage updateBowl(Bowl bowl) {
        return null;
    }

    @Override
    public MyMessage addBowl(Bowl bowl) {
        return null;
    }
}
