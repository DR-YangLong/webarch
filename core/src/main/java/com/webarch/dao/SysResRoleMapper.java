package com.webarch.dao;


import com.webarch.common.spring.annotation.MyBatisRepository;
import com.webarch.model.SysResRole;

@MyBatisRepository
public interface SysResRoleMapper {
    
    int deleteByPrimaryKey(Integer id);
    
    int insert(SysResRole record);
    
    int insertSelective(SysResRole record);
    
    SysResRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysResRole record);

    int updateByPrimaryKey(SysResRole record);
}