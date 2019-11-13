package com.mju.band3.community.DTO;

import com.mju.band3.community.Model.User;
import lombok.Data;

@Data
public class CommentReviewDTO {
    private Long id;

    private Long parentId;

    private Integer type;

    private Integer commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private String content;

    private User user;

    private Long commentCount;
}
