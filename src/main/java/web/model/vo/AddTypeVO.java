package web.model.vo;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Alan on 2016/12/8.
 */
public class AddTypeVO {
    String name;
    MultipartFile pic;

    public AddTypeVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getPic() {
        return pic;
    }

    public void setPic(MultipartFile pic) {
        this.pic = pic;
    }
}
