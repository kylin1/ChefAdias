package web.tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
public class MyFile {

    private static final String LOCAL_FOLDER = "/data/wwwroot/default/images/";

    private static final String HTTP_FOLDER = "http://139.196.179.145/images/";

//    private static final String LOCAL_IMG_FOLDER = "/Users/kylin/Desktop/";

    /**
     * 保存文件到本地
     *
     * @param file
     * @return 本地路径
     */
    public static String saveFile(MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            //生成uuid作为文件前缀名称
            String filePrefix = UUID.randomUUID().toString().replaceAll("-", "");

            //获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType = file.getContentType();

            //获得文件后缀名称
            String fileSuffix = contentType.substring(contentType.indexOf("/") + 1);

            //拼接获得文件完整名称
            String fileName = filePrefix + "." + fileSuffix;

            //保存文件的路径
            String savedFile = LOCAL_FOLDER + fileName;

            //保存到硬盘
            file.transferTo(new File(savedFile));

            //返回给前端的HTTP路径
            return HTTP_FOLDER + fileName;
        }
        return "save file failed";
    }

}
