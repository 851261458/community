package com.mju.band3.community.Model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String token;
    private String accountId;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;


}
