package web.biz;

import org.springframework.web.multipart.MultipartFile;
import web.model.User;
import web.model.exceptions.DataConflictException;
import web.model.exceptions.NotFoundException;
import web.model.exceptions.ServerException;
import web.model.exceptions.WrongInputException;

/**
 * Created by kylin on 03/12/2016.
 * All rights reserved.
 */
public interface IUserService {

    /**
     * 用户登录
     *
     * @param email 邮箱
     * @param password 密码
     * @return 用户信息(登录成功)
     *
     * 失败则抛出异常:
     * @throws NotFoundException 用户不存在
     * @throws WrongInputException 密码错误
     */
    User login(String email, String password)
            throws NotFoundException,WrongInputException;

    /**
     * 注册
     *
     * @param email 邮箱
     * @param password 密码
     * @param username 用户名
     * @return 注册结果
     *
     * 失败则抛出异常
     * @throws DataConflictException 用户邮箱已经存在
     */
    User register(String email, String password, String username)
            throws DataConflictException;

    /**
     * 修改收货地址
     *
     * @return
     */
    boolean changeAddress(int userId, String newAddress) throws NotFoundException;

    /**
     * 修改手机号
     *
     * @param userId
     * @param newPhone
     * @return
     * @throws NotFoundException
     */
    boolean changePhone(int userId, String newPhone) throws NotFoundException;


    /**
     * 修改头像
     *
     * @param userId
     * @param newAvatar
     * @return
     */
    boolean changeAvatar(int userId,MultipartFile newAvatar) throws NotFoundException, ServerException;

    /**
     *  获取用户信息
     *
     * @param userId
     * @return
     */
    User getUser(int userId) throws NotFoundException;

}
