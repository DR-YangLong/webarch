package com.webarch.dao;


import com.webarch.common.spring.annotation.MyBatisRepository;
import com.webarch.model.SysResources;

@MyBatisRepository
public interface SysResourcesMapper {

    int deleteByPrimaryKey(Integer resId);

    int insert(SysResources record);

    int insertSelective(SysResources record);

    SysResources selectByPrimaryKey(Integer resId);

    int updateByPrimaryKeySelective(SysResources record);

    int updateByPrimaryKey(SysResources record);
}