package com.bjtu.afms.service;

import com.bjtu.afms.mapper.CommentMapper;
import com.bjtu.afms.model.Comment;
import com.bjtu.afms.model.CommentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    public int insertComment(Comment comment) {
        return commentMapper.insertSelective(comment);
    }

    public int deleteComment(int commentId) {
        return commentMapper.deleteByPrimaryKey(commentId);
    }

    public int updateComment(Comment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    public Comment selectComment(int commentId) {
        return commentMapper.selectByPrimaryKey(commentId);
    }

    public List<Comment> selectCommentList(Comment comment, String orderByClause) {
        CommentExample example = new CommentExample();
        if (StringUtils.isNoneBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        CommentExample.Criteria criteria = example.createCriteria();
        if (comment.getUserId() != null) {
            criteria.andUserIdEqualTo(comment.getUserId());
        }
        if (comment.getType() != null) {
            criteria.andTypeEqualTo(comment.getType());
        }
        if (comment.getRelateId() != null) {
            criteria.andRelateIdEqualTo(comment.getRelateId());
        }
        return commentMapper.selectByExample(example);
    }

}
