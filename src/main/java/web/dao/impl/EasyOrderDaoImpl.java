package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.EasyOrderDao;
import web.dao.opearion.EasyOrderOperation;
import web.model.EasyOrder;
import web.tools.MyMessage;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("easyOrderDao")
public class EasyOrderDaoImpl implements EasyOrderDao {

    SqlSession sqlSession;
    EasyOrderOperation opeation;

    @Override
    public EasyOrder getEasyOrderOfUser(int userId) {
        return null;
    }

    @Override
    public MyMessage addEasyOrder(EasyOrder easyOrder) {
        return null;
    }
}
