package com.bjtu.afms.controller;

import com.bjtu.afms.biz.CommentBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Comment;
import com.bjtu.afms.service.CommentService;
import com.bjtu.afms.web.param.query.CommentQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private CommentBiz commentBiz;

    @GetMapping("/comment/info/{commentId}")
    public Result getCommentInfo(@PathVariable("commentId") int id) {
        Comment comment = commentService.selectComment(id);
        if (comment != null) {
            return Result.ok(comment);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/my/comment/list", "/my/comment/list/{page}"})
    public Result getMyCommentList(@PathVariable(value = "page", required = false) Integer page) {
        CommentQueryParam param = new CommentQueryParam();
        param.setUserId(LoginContext.getUserId());
        param.setOrderBy("add_time desc");
        return Result.ok(commentBiz.getCommentList(param, page));
    }

    @GetMapping({"/comment/relate", "/comment/relate/{page}"})
    public Result getRelateComment(CommentQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(commentBiz.getCommentList(param, page));
    }

    @AuthCheck(auth = AuthType.ADMIN)
    @GetMapping({"/comment/all", "/comment/all/{page}"})
    public Result getAllComment(CommentQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(commentBiz.getCommentList(param, page));
    }

    @AuthCheck(auth = AuthType.ADMIN)
    @GetMapping({"/comment/list", "/comment/list/{page}"})
    public Result getCommentList(CommentQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(commentBiz.getCommentList(param, page));
    }

    @PostMapping("/comment/insert")
    public Result addComment(@RequestBody Comment comment) {
        if (commentBiz.insertComment(comment)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(owner = true, data = DataType.COMMENT)
    @PostMapping("/comment/info/modify")
    public Result modifyCommentInfo(@RequestParam("id") int id, @RequestParam("score") int score,
                                    @RequestParam("content") String content) {
        if (commentBiz.modifyComment(id, score, content)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = AuthType.ADMIN, owner = true, data = DataType.COMMENT)
    @PostMapping("/comment/delete/{commentId}")
    public Result deleteComment(@PathVariable("commentId") int id) {
        if (commentBiz.deleteComment(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
