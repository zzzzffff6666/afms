package com.bjtu.afms.service;

import com.bjtu.afms.mapper.CommentMapper;
import com.bjtu.afms.model.Comment;
import com.bjtu.afms.model.CommentExample;
import com.bjtu.afms.web.param.query.CommentQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    public List<Comment> selectCommentList(CommentQueryParam param) {
        CommentExample example = new CommentExample();
        if (StringUtils.isNoneBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        CommentExample.Criteria criteria = example.createCriteria();
        if (param.getUserId() != null) {
            criteria.andUserIdEqualTo(param.getUserId());
        }
        if (param.getType() != null) {
            criteria.andTypeEqualTo(param.getType());
        }
        if (param.getRelateId() != null) {
            criteria.andRelateIdEqualTo(param.getRelateId());
        }
        if (param.getAddBegin() != null || param.getAddLast() != null) {
            if (param.getAddBegin() == null) {
                param.setAddBegin(new Date(0L));
            }
            if (param.getAddLast() == null) {
                param.setAddLast(new Date());
            }
            criteria.andAddTimeBetween(param.getAddBegin(), param.getAddLast());
        }
        return commentMapper.selectByExample(example);
    }

}
