package com.mju.band3.community.Mapper;

import com.mju.band3.community.Model.Question;
import com.mju.band3.community.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(String token);
    @Select("select * from user where account_id=#{id}")
    User findByAccount_Id(Integer id);
    @Update("update user set name=#{name},gmt_modified=#{gmtModified},token=#{token},avatar_Url=#{avatarUrl} where id=#{id}")
    void update(User byAccount_id);

    List<User> selectUserIdIn(List<Integer> userId);
    @Select("select name from user where account_id=#{commentator}")
    String findNameByAccount_Id(Integer commentator);
}
