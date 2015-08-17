package com.webarch.dao;


import com.webarch.common.spring.annotation.MyBatisRepository;
import com.webarch.model.User;

import java.util.Map;

@MyBatisRepository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(User record);
    int insertSelective(User record);
    User selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(User record);
    int updateByPrimaryKey(User record);
    User selectPrivileges(Integer id);
    Map<String,Object> selectIdAndPasswordByUserName(String account);
    Integer selectIdentity(String account);
    User selectUserByAccount(String account);
}