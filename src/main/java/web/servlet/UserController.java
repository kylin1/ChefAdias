package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import web.biz.UserService;
import web.model.User;
import web.model.exceptions.DataConflictException;
import web.model.exceptions.NotFoundException;
import web.model.exceptions.ServerException;
import web.model.exceptions.WrongInputException;
import web.tools.MyConverter;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kylin on 03/12/2016.
 * All rights reserved.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public
    @ResponseBody
    String login(HttpServletRequest request) {
        //获取参数
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = this.userService.login(email,password);
            //登录成功,返回结果
            return this.getUserResult(user);

            //用户邮箱不存在
        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(),e.getMessage(),"");

            //密码不正确
        } catch (WrongInputException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(),e.getMessage(),"");
        }

    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public
    @ResponseBody
    String register(HttpServletRequest request) {
        //获取参数
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //调用逻辑层
        try {
            User newUser = this.userService.register(email,password,username);
            //注册成功,返回信息
            return this.getUserResult(newUser);

            //逻辑处理异常
        } catch (DataConflictException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(),e.getMessage(),"");
        }
    }

    @RequestMapping(value = "/modAddr", method = RequestMethod.POST)
    public
    @ResponseBody
    String modAddr(HttpServletRequest request) {
        //获取参数
        String userid = request.getParameter("userid");
        int intId = MyConverter.getInt(userid);

        String addr = request.getParameter("addr");
        try {
            this.userService.changeAddress(intId,addr);
            return MyResponse.success("");
        } catch (NotFoundException e) {
            return MyResponse.failure(e.getErrorCode(),e.getMessage(),"");
        }
    }

    @RequestMapping(value = "/modPhone", method = RequestMethod.POST)
    public
    @ResponseBody
    String modPhone(HttpServletRequest request) {
        //获取参数
        String userid = request.getParameter("userid");
        int intId = MyConverter.getInt(userid);

        String phone = request.getParameter("phone");
        try {
            this.userService.changePhone(intId,phone);
            return MyResponse.success("");
        } catch (NotFoundException e) {
            return MyResponse.failure(e.getErrorCode(),e.getMessage(),"");
        }
    }

    @RequestMapping(
            value = "/modAva",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String changeAvatar(@RequestParam(value = "userid", required = true) String userid,
                              @RequestParam(value = "avatar", required = true) MultipartFile avatar,
                              HttpServletRequest request){
        int intId = Integer.parseInt(userid);
        try {
            this.userService.changeAvatar(intId,avatar);
            return MyResponse.success();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(),e.getMessage());
        } catch (ServerException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(),e.getMessage());
        }
    }

    @RequestMapping(
            value = "/getInfo",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getInfo(HttpServletRequest request){
        String userid = request.getParameter("userid");
        int intId = MyConverter.getInt(userid);

        try {
            User user = this.userService.getUser(intId);
            return this.getUserInfo(user);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(),e.getMessage());
        }
    }


    /**
     * 根据返回的用户信息提取URL参数返回前端
     *
     * @param user
     * @return
     */
    private String getUserResult(User user){
        int id = user.getId();
        String userName = user.getUsername();
        String avatar = user.getAvatar();
        Map<String,String> result = new HashMap<>();
        result.put("userid",Integer.toString(id));
        result.put("username",userName);
        result.put("avatar",avatar);
        return MyResponse.success(result);
    }

    private String getUserInfo(User user){
        Map<String,String> result = new HashMap<>();
        result.put("phone",user.getPhone());
        result.put("addr",user.getAddress());
        return MyResponse.success(result);
    }

}
