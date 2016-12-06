package web.model.exceptions;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
public class ErrorCode {

    //网络错误
    public static final String NETWORK = "0000";

    //内部错误
    public static final String SERVER = "1111";
    public static final String JSON = "2222";

    //用户名错误
    public static final String WRONG_USERNAME = "0001";

    //密码错误
    public static final String WRONG_PASSWORD = "0002";

    //登录次数过多
    public static final String TRY_TIMES = "0003";

    //邮箱已被注册
    public static final String EMAIL_TAKEN = "0004";

    //用户名已被注册
    public static final String USERNAME_TAKEN = "0005";

    //无此用户
    public static final String NO_USER = "0006";

    //菜品种类不存在
    public static final String NO_TYPE_OF_FOOD = "0010";

}
