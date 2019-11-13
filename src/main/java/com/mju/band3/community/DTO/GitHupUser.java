package com.mju.band3.community.DTO;

import lombok.Data;

@Data
public class GitHupUser {
    private String name;
    private String bio ;
    private Long id;
    private String avatarUrl;





    @Override
    public String toString() {
        return "GitHupUser{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", id=" + id +
                '}';
    }
}