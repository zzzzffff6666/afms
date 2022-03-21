package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Comment;
import com.bjtu.afms.web.param.query.CommentQueryParam;

public interface CommentBiz {

    Page<Comment> getCommentList(CommentQueryParam param, Integer page);

    boolean insertComment(Comment comment);

    boolean modifyComment(int id, int score, String content);

    boolean deleteComment(int commentId);
}
