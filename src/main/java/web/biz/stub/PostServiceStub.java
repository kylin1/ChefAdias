package web.biz.stub;

import web.biz.PostService;
import web.model.communication.Post;
import web.model.communication.PostBasicInfo;
import web.model.communication.PostComment;
import web.model.communication.PostViews;
import web.model.enums.PostViewAttitude;

import java.util.Date;
import java.util.List;

/**
 * Created by kylin on 9/12/16.
 * All rights reserved.
 */

public class PostServiceStub implements PostService {
    public boolean publish(PostBasicInfo basicInfo, String content) {
        return false;
    }

    public boolean comment(String postID, PostComment comment) {
        return false;
    }

    public void delete(String postID) {

    }

    public boolean attitude(String postID, PostViewAttitude attitude) {
        return false;
    }

    public void browse(String postID) {

    }

    public Post search(String keyword) {
        return this.randomPost();
    }

    public Post getPost(String postID) {
        return new Post();
    }

    public List<String> getAllPost() {
        return null;
    }

    private Post randomPost() {
        Post post1 = new Post();
        PostBasicInfo basic = new PostBasicInfo();
        basic.setPostID("222");
        basic.setAuthor("王嘉琛");
        basic.setDate(new Date());
        basic.setTitle("第x个");
        post1.setBasicInfo(basic);

        post1.setContent("<p> 1233 </p>");
        PostViews postViews = new PostViews();
        postViews.setLikes(100);
        postViews.setThanks(200);
        postViews.setDisagrees(10);
        post1.setViews(postViews);

        return post1;
    }
}
