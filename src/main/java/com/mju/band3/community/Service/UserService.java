package com.mju.band3.community.Service;

import com.mju.band3.community.Mapper.UserMapper;
import com.mju.band3.community.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;


    public void insertOrUpdate(User user) {
        User byAccount_id = userMapper.findByAccount_Id(Integer.valueOf(user.getAccountId()));
        if (byAccount_id==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

        }else{
            byAccount_id.setAvatarUrl(user.getAvatarUrl());
            byAccount_id.setGmtModified(System.currentTimeMillis());
            byAccount_id.setName(user.getName());
            byAccount_id.setToken(user.getToken());
            userMapper.update(byAccount_id);

        }


    }
}
