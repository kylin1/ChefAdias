package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.EasyOrderService;
import web.dao.EasyOrderDao;
import web.model.po.EasyOrder;
import web.model.exceptions.ErrorCode;
import web.model.exceptions.NotFoundException;
import web.tools.MyMessage;

/**
 * Created by Alan on 2016/12/5.
 */
@Service
public class EasyOrderImpl implements EasyOrderService {
    @Autowired
    EasyOrderDao dao;

    @Override
    public EasyOrder getEasyOrder(int userID) throws NotFoundException {
        EasyOrder easyOrder = dao.getEasyOrderOfUser(userID);
        if (easyOrder == null) {
            throw new NotFoundException(ErrorCode.SERVER);
        } else {
            return easyOrder;
        }
    }

    @Override
    public MyMessage addEasyOrder(int userID, int orderID) throws NotFoundException {
        return null;
    }
}
