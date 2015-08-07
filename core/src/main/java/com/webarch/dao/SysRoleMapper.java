package com.webarch.dao;


import com.webarch.common.spring.annotation.MyBatisRepository;
import com.webarch.model.SysRole;

@MyBatisRepository
public interface SysRoleMapper {
    
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);
    
    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}