package com.webarch.dao;


import com.webarch.common.spring.annotation.MyBatisRepository;
import com.webarch.model.User;

@MyBatisRepository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(User record);
    int insertSelective(User record);
    User selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(User record);
    int updateByPrimaryKey(User record);
}