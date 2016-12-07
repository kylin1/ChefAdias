package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.ShopOrderService;
import web.dao.UserDao;
import web.model.vo.ShopOrderItemVO;
import web.model.vo.ShopOrderVO;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
@Service
public class ShopOrderImpl implements ShopOrderService {

    @Autowired
    UserDao userDao;

    @Override
    public ShopOrderVO getOrder(int orderID) {
        return null;
    }

    @Override
    public List<ShopOrderItemVO> getOrderList(String date) {
        return null;
    }

    @Override
    public MyMessage setState(int orderID, int state) {
        return null;
    }
}
