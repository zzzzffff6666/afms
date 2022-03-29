package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.CommentBiz;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
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

    @Resource
    private LogBiz logBiz;

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
            logBiz.saveLog(DataType.COMMENT, comment.getId(), OperationType.INSERT_COMMENT,
                    null, JSON.toJSONString(comment));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyComment(int id, int score, String content) {
        Comment old = commentService.selectComment(id);
        if (old == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.setId(id);
        comment.setScore(score);
        comment.setContent(content);
        if (commentService.updateComment(comment) == 1) {
            logBiz.saveLog(DataType.COMMENT, comment.getId(), OperationType.UPDATE_COMMENT,
                    JSON.toJSONString(old), JSON.toJSONString(comment));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteComment(int commentId) {
        Comment old = commentService.selectComment(commentId);
        if (old == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (commentService.deleteComment(commentId) == 1) {
            permissionBiz.deleteResource(DataType.COMMENT.getId(), commentId);
            logBiz.saveLog(DataType.COMMENT, commentId, OperationType.DELETE_COMMENT,
                    JSON.toJSONString(old), null);
            return true;
        } else {
            return false;
        }
    }
}
