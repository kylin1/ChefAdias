package web.dao.opearion;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.model.communication.Post;
import web.model.communication.PostBasicInfo;
import web.model.communication.PostComment;
import web.model.communication.PostViews;

import java.util.List;

/**
 * Created by JiachenWang on 2016/8/16.
 */
public interface PostOperation {

    @Select("select postID from post")
    List<String> getAllPost();

    @Select("select max(postID) from post")
    int getNewPostID();

    @Select("select max(ID) from post_comment where postID=#{postID}")
    int getNewCommentID(String postID);

    @Select("select * from post_basic where postID=#{postID}")
    PostBasicInfo getBasicInfoByID(String postID);

    @Select("select * from post_comment where postID=#{postID}")
    List<PostComment> getPostCommentByID(String postID);

    @Select("select * from post_view where postID=#{postID}")
    PostViews getPostViewsByID(String postID);

    @Select("select * from post where postID=#{postID}")
    Post getPostMainByID(String postID);

    @Update("update post_view set viewsNum=#{viewsNum},thanks=#{thanks},likes=#{likes},disagrees=#{disagrees} where postID=#{postID}")
    void updateViews(PostViews views);

    @Insert("insert into post_comment(postID,ID,author,date,content)  \n" +
            "             values(#{postID},#{ID},#{author},#{date},#{content})")
    void insertComment(PostComment comment);

    @Delete("delete from post_comment where postID=#{0} and ID=#{1}")
    void deleteComment(String postID, int ID);

    @Delete("delete from post_comment where postID=#{postID}")
    void deleteComments(String postID);

    @Insert("insert into post_view(postID,viewsNum,thanks,likes,disagrees)  \n" +
            "             values(#{postID},#{viewsNum},#{thanks},#{likes},#{disagrees})")
    void insertPostViews(PostViews views);

    @Delete("delete from post_view where postID=#{postID}")
    void deletePostViews(String postID);

    @Insert("insert into post_basic(postID,title,author,date,topic)  \n" +
            "             values(#{postID},#{title},#{author},#{date},#{topic})")
    void insertBasicInfo(PostBasicInfo info);

    @Delete("delete from post_basic where postID=#{postID}")
    void deleteBasicInfo(String postID);

    @Insert("insert into post(postID,content)  \n" +
            "             values(#{postID},#{content})")
    void insertPostMain(Post post_main);

    @Delete("delete from post where postID=#{postID}")
    void deletePostMain(String postID);

    @Select("select postID from post_basic where title like #{key}")
    List<String> search(String key);

}
