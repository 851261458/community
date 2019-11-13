package com.mju.band3.community.Controller;

import com.mju.band3.community.DTO.CommentDTO;
import com.mju.band3.community.DTO.CommentReviewDTO;
import com.mju.band3.community.DTO.ResultDTO;
import com.mju.band3.community.Exception.CustomizeErrorCode;
import com.mju.band3.community.Model.Comment;
import com.mju.band3.community.Model.User;
import com.mju.band3.community.Service.CommentService;
import com.mju.band3.community.enums.CommentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller

public class CommentController {
    @Autowired
    CommentService commentService;
    @ResponseBody
    @PostMapping(value = "/comment")
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentDTO==null|| StringUtils.isEmpty(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);

        }
        Comment comment=new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setParentId(commentDTO.getParentID());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(Integer.valueOf(user.getAccountId()));
        comment.setLikeCount(0L);
        comment.setCommentCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}")
    public ResultDTO comments(@PathVariable(name = "id")Integer id){
        List<CommentReviewDTO> commentReviewDTOList=
                commentService.listByTargetId(id, CommentTypeEnum.COMMENT.getType());
        return ResultDTO.okOf(commentReviewDTOList);
    }


}
