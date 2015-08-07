package com.webarch.dao;

import com.webarch.common.spring.annotation.MyBatisRepository;
import com.webarch.model.SysPermission;

@MyBatisRepository
public interface SysPermissionMapper {
    
    int deleteByPrimaryKey(Integer permId);

    int insert(SysPermission record);
    
    int insertSelective(SysPermission record);
    
    SysPermission selectByPrimaryKey(Integer permId);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}