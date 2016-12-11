package biz;

import org.junit.Test;
import web.biz.EasyOrderService;
import web.biz.impl.EasyOrderImpl;
import web.model.exceptions.NotFoundException;
import web.model.po.EasyOrder;
import web.model.vo.EasyOrderVO;

/**
 * Created by Alan on 2016/12/9.
 */
public class EasyOrderTest {
    @Test
    public void testGetEO() {
        EasyOrderService service = new EasyOrderImpl();
        try {
            EasyOrderVO easyOrder = service.getEasyOrder(1);
            if (easyOrder != null) {
                System.out.println(easyOrder.getFood_list());
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
