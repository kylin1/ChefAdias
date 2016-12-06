package web.servlet;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import web.tools.MyFile;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/file")
public class PictureController {

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    private String fildUpload(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        String savedFilePath = MyFile.saveFile(file);
        System.out.println("newFile=" + savedFilePath);
        return "showPicture";
    }

    //因为我的JSP在WEB-INF目录下面，浏览器无法直接访问
    @RequestMapping(value = "forward")
    private String forward() {
        return "uploadPicture";
    }
}