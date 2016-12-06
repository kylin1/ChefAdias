package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import web.biz.UserService;
import web.model.po.User;
import web.model.exceptions.DataConflictException;
import web.model.exceptions.NotFoundException;
import web.model.exceptions.ServerException;
import web.model.exceptions.WrongInputException;
import web.model.vo.UserInfoVO;
import web.model.vo.UserVO;
import web.tools.BeanTool;
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
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public
    @ResponseBody
    String login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            UserVO user = userService.login(email, password);
            return MyResponse.success(BeanTool.bean2Map(user));

        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(), e.getMessage());

        } catch (WrongInputException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public
    @ResponseBody
    String register(HttpServletRequest request) {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UserVO newUser = userService.register(email, password, username);
            return MyResponse.success(BeanTool.bean2Map(newUser));

        } catch (DataConflictException | NotFoundException | WrongInputException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "modAddr", method = RequestMethod.POST)
    public
    @ResponseBody
    String modAddr(HttpServletRequest request) {
        String userid = request.getParameter("userid");
        int intId = MyConverter.getInt(userid);
        String addr = request.getParameter("addr");

        try {
            userService.changeAddress(intId, addr);
            return MyResponse.success();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "modPhone", method = RequestMethod.POST)
    public
    @ResponseBody
    String modPhone(HttpServletRequest request) {
        String userid = request.getParameter("userid");
        int intId = MyConverter.getInt(userid);
        String phone = request.getParameter("phone");

        try {
            userService.changePhone(intId, phone);
            return MyResponse.success();

        } catch (NotFoundException e) {
            return MyResponse.failure(e.getErrorCode(), e.getMessage());
        }
    }

    @RequestMapping(
            value = "modAva",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String changeAvatar(@RequestParam(value = "userid") String userid,
                               @RequestParam(value = "avatar") MultipartFile avatar) {
        int intId = Integer.parseInt(userid);
        try {
            this.userService.changeAvatar(intId, avatar);
            return MyResponse.success();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(), e.getMessage());
        } catch (ServerException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(), e.getMessage());
        }
    }

    @RequestMapping(
            value = "getInfo",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getInfo(HttpServletRequest request) {
        String userid = request.getParameter("userid");
        int intId = MyConverter.getInt(userid);

        try {
            UserInfoVO user = userService.getUserInfo(intId);
            return MyResponse.success(BeanTool.bean2Map(user));

        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(), e.getMessage());
        }
    }
}
