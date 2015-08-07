package com.webarch.dao;


import com.webarch.common.spring.annotation.MyBatisRepository;
import com.webarch.model.SysResPerm;

@MyBatisRepository
public interface SysResPermMapper {
    
    int deleteByPrimaryKey(Integer id);

    int insert(SysResPerm record);

    int insertSelective(SysResPerm record);

    SysResPerm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysResPerm record);

    int updateByPrimaryKey(SysResPerm record);
}