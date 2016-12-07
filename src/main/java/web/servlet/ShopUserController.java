package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.ShopUserService;
import web.model.exceptions.ErrorCode;
import web.model.vo.ShopUserVO;
import web.tools.JsonConverter;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Alan on 2016/12/7.
 */
@Controller
@RequestMapping("shop")
public class ShopUserController {

    @Autowired
    ShopUserService service;

    @RequestMapping(
            value = "getUserList",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getUserList() {
        List<ShopUserVO> userList = service.getUserList();
        return MyResponse.success(userList);
    }

}
