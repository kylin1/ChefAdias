package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.CustomerMenuService;
import web.biz.UserCustMenuService;
import web.model.exceptions.ErrorCode;
import web.model.vo.AddCustMenuVO;
import web.model.vo.CustMenuDetailVO;
import web.model.vo.CustMenuInfoVO;
import web.model.vo.CustMenuItemVO;
import web.tools.MyConverter;
import web.tools.MyMessage;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Alan on 2016/12/14.
 */
@Controller
@RequestMapping("user")
public class UserCustMenuController {
    @Autowired
    private UserCustMenuService service;

    @RequestMapping(
            value = "getMList",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getMList(HttpServletRequest request) {
        String userID = request.getParameter("userid");
        List<CustMenuItemVO> menuList = service.getMList(MyConverter.getInt(userID));
        if (menuList != null) {
            return MyResponse.success(menuList);
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to get custom list");
        }
    }

    @RequestMapping(
            value = "getMMenuInfo",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getMMenuInfo() {
        List<CustMenuInfoVO> menuInfoList = service.getMMenuInfo();
        if (menuInfoList != null) {
            return MyResponse.success(menuInfoList);
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to get custom menu info");
        }
    }

    @RequestMapping(
            value = "getMMenu",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getMMenu(HttpServletRequest request) {
        String mmenuID = request.getParameter("mmenuid");
        CustMenuDetailVO menuDetailVO = service.getMMenu(MyConverter.getInt(mmenuID));
        if (menuDetailVO != null) {
            return MyResponse.success(menuDetailVO);
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to get menu");
        }
    }

    @RequestMapping(
            value = "addMMenu",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String addMMenu(@RequestBody AddCustMenuVO addCustMenuVO) {
        MyMessage myMessage = service.addMMenu(addCustMenuVO);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to add menu");
        }
    }

    @RequestMapping(
            value = "deleteMMenu",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String deleteMMenu(HttpServletRequest request) {
        String mmenuID = request.getParameter("mmenuid");
        MyMessage myMessage = service.deleteMMenu(MyConverter.getInt(mmenuID));
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to delete menu");
        }
    }
}
