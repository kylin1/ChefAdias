package web.model.vo;

/**
 * Created by Alan on 2016/12/7.
 */
public class UserInfoVO {
    String phone;
    String addr;

    public UserInfoVO(String phone, String addr) {
        this.phone = phone;
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
