package web.dao.opearion;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import web.model.po.User;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface UserOperation {

    @Select({"select * from user"})
    List<User> getAllUser();

    @Select({"select * from user where username like #{name}"})
    List<User> searchUser(String name);

    @Select({"select * from user where id = #{id}"})
    User getUser(int id);

    @Select({"select * from user where email = #{email}"})
    User getUserByMail(String email);

    @Insert({"insert into user(username,password,email,address,phone)"+
            " values(#{username},#{password},#{email},#{address},#{phone})"})
    void save(User user);

    @Insert({"update user set username=#{username},password=#{password},email=#{email}," +
            "avatar=#{avatar},address=#{address},phone=#{phone} where id = #{id}"})
    void update(User user);
}
