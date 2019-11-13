package com.mju.band3.community.Service;

import com.mju.band3.community.DTO.CommentReviewDTO;
import com.mju.band3.community.Exception.CustomizeErrorCode;
import com.mju.band3.community.Exception.CustomizeException;
import com.mju.band3.community.Mapper.CommentMapper;
import com.mju.band3.community.Mapper.NotificationMapper;
import com.mju.band3.community.Mapper.QuestionMapper;
import com.mju.band3.community.Mapper.UserMapper;
import com.mju.band3.community.Model.*;
import com.mju.band3.community.enums.CommentTypeEnum;
import com.mju.band3.community.enums.NotificationEnum;
import com.mju.band3.community.enums.NotificationStatusEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

            commentMapper.incCommentCount(comment.getParentId());
            //创建通知
            createNotify(comment, NotificationEnum.REPLY_COMMENT.getType(), dbComment.getCommentator().longValue());
        }else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);

            questionMapper.updateCommentCount(question.getId());

            createNotify(comment, NotificationEnum.REPLY_QUESTION.getType(), question.getCreator().longValue());
        }

        }

    private void createNotify(Comment comment, int type, Long receiver) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type);
        notification.setOuterid(comment.getParentId().intValue());
        notification.setNotifier(comment.getCommentator().longValue());
        notification.setReceiver(receiver);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insert(notification);
    }

    public List<CommentReviewDTO> listByTargetId(Integer id, Integer type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id.longValue())
                .andTypeEqualTo(type);
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size()==0){
            return new ArrayList<>();
        }

//      获取去重的评论人
        Set<Integer> set = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userId=new ArrayList<>();
        userId.addAll(set);


//      获取对应的评论人并转化为map，
        List<User> users = userMapper.selectUserIdIn(userId);
//            Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));


//      转化comment为CommentReviewDTO
//        List<CommentReviewDTO> commentReviewDTOS=comments.stream().map(comment -> {
//            CommentReviewDTO commentReviewDTO=new CommentReviewDTO();
//            BeanUtils.copyProperties(comment,commentReviewDTO);
//            commentReviewDTO.setUser(userMap.get(comment.getCommentator()));
//            return commentReviewDTO;
//        }).collect(Collectors.toList());
//        return commentReviewDTOS;

        List<CommentReviewDTO> commentReviewDTOS=new ArrayList<>();
        for (Comment comment : comments) {
            CommentReviewDTO commentReviewDTO=new CommentReviewDTO();
            BeanUtils.copyProperties(comment,commentReviewDTO);
            for (User user : users) {
                if (Integer.valueOf(user.getAccountId()).equals(commentReviewDTO.getCommentator())){
                    commentReviewDTO.setUser(user);
                    commentReviewDTOS.add(commentReviewDTO);
                }
            }
        }

        return commentReviewDTOS;
    }

}
