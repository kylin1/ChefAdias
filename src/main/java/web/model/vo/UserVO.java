package web.model.vo;

/**
 * Created by Alan on 2016/12/7.
 */
public class UserVO {
    String userid;
    String username;
    String avatar;

    public UserVO(String userid, String username, String avatar) {
        this.userid = userid;
        this.username = username;
        this.avatar = avatar;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
