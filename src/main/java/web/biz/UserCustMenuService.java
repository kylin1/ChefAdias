package web.biz;

import web.model.vo.AddCustMenuVO;
import web.model.vo.CustMenuDetailVO;
import web.model.vo.CustMenuInfoVO;
import web.model.vo.CustMenuItemVO;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by Alan on 2016/12/14.
 */
public interface UserCustMenuService {

    /**
     * 获取用户的自定义菜单列表
     *
     * @param userID
     * @return
     */
    List<CustMenuItemVO> getMList(int userID);

    /**
     * 获取自定义菜单内容信息
     *
     * @return
     */
    List<CustMenuInfoVO> getMMenuInfo();

    /**
     * 获取自定义菜单内容
     *
     * @param mmenuID
     * @return
     */
    CustMenuDetailVO getMMenu(int mmenuID);

    /**
     * 删除自定义菜单
     *
     * @param mmenuID
     * @return
     */
    MyMessage deleteMMenu(int mmenuID);

    /**
     * 添加自定义菜单
     *
     * @param addMMenuVO
     * @return
     */
    MyMessage addMMenu(AddCustMenuVO addMMenuVO);

}
