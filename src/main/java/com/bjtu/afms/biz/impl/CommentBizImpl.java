package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.CommentBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Comment;
import com.bjtu.afms.service.CommentService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.CommentQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class CommentBizImpl implements CommentBiz {

    @Resource
    private CommentService commentService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Override
    public Page<Comment> getCommentList(CommentQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Comment> pageInfo = new PageInfo<>(commentService.selectCommentList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean insertComment(Comment comment) {
        comment.setModTime(null);
        comment.setModUser(null);
        comment.setUserId(LoginContext.getUserId());
        if (commentService.insertComment(comment) == 1) {
            permissionBiz.initResourceOwner(DataType.COMMENT.getId(), comment.getId(), comment.getUserId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyComment(int id, int score, String content) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setScore(score);
        comment.setContent(content);
        return commentService.updateComment(comment) == 1;
    }

    @Override
    @Transactional
    public boolean deleteComment(int commentId) {
        permissionBiz.deleteResource(DataType.COMMENT.getId(), commentId);
        return commentService.deleteComment(commentId) == 1;
    }
}
