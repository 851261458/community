package com.mju.band3.community.Mapper;

import com.mju.band3.community.DTO.QuestionDTO;
import com.mju.band3.community.Model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void AskQuestion(Question question);
    //查出所有问题与对应用户
    List<QuestionDTO> selectAll();

    @Select("select count(1) from question")
    Integer count();
    //查出当前登录用户的问题与用户信息
    List<QuestionDTO> list( Integer userId);

    @Select("select count(1) from question  where creator=#{userId}")
    Integer countByUserId(@Param(value = "userId")Integer userId);

    @Select("select * from question where id=#{id}")
    Question getById(Integer id);
    @Update("update question set title=#{title}, description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);
    @Update("update question set view_count =view_count+1 where id=#{id} ")
    void updateViewCount(Integer id);
    @Select("select * from question where id=#{parentId}")
    Question selectByPrimaryKey(Long parentId);
    @Update("update question set comment_count =comment_count+1 where id=#{id} ")
    void updateCommentCount(Integer id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);

    List<QuestionDTO> search(String search);
    @Select("select * from question limit #{offset},#{limit}")
    List<Question> selectWithRowsbounds(@Param("offset")int  offset,@Param("limit") int limit);
}
