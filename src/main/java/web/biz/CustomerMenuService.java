package web.biz;

import org.springframework.web.multipart.MultipartFile;
import web.model.vo.CustOrderInfoVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
public interface CustomerMenuService {

    /**
     * 添加自定义菜单内容
     *
     * @param type  类型1-6
     * @param name  分类菜名
     * @param price 价格
     * @return menuID
     */
    int addMMenu(int type, String name, BigDecimal price);

    /**
     * 上传自定义食物图片
     *
     * @param foodID
     * @param pic
     * @return
     */
    boolean uploadCustFoodPic(int foodID, MultipartFile pic);

    /**
     * 修改自定义菜单内容
     *
     * @param menuId 目标自定义菜单食物 ID
     * @param type   类型1-6
     * @param name   分类菜名
     * @param price  价格
     * @return
     */
    boolean modMMenu(int menuId, int type, String name, BigDecimal price);


    /**
     * 删除自定义菜单内容
     *
     * @param menuId 目标自定义菜单食物 ID
     * @return
     */
    boolean deleteMMenu(int menuId);

    /**
     * 获取自定义菜单订单列表
     *
     * @param date
     * @return
     */
    List<CustOrderVO> getCustOrderList(Date date);

    /**
     * 获取自定义菜单订单详情
     *
     * @param orderID
     * @return
     */
    CustOrderInfoVO getCustOrder(int orderID);

}
