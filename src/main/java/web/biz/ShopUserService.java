package web.biz;

import org.springframework.stereotype.Service;
import web.model.vo.ShopUserVO;
import web.model.vo.UserVO;
import web.tools.MyMessage;

import java.util.List;

/**
 * 商户操作用户模块
 * Created by Alan on 2016/12/7.
 */
public interface ShopUserService {

    /**
     * 获取所有用户列表
     *
     * @return 用户列表
     */
    List<ShopUserVO> getUserList();

    /**
     * 搜索用户
     *
     * @param username 用户名的部分或全部
     * @return 可能的用户列表
     */
    List<UserVO> searchUser(String username);

    /**
     * 设置还碗情况
     *
     * @param userID 用户ID
     * @param state  还碗情况 0未还/1已还
     * @return MyMessage
     */
    MyMessage setBowl(int userID, int state);
}
