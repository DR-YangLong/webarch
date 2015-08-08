package com.webarch.dao;


import com.webarch.common.spring.annotation.MyBatisRepository;
import com.webarch.model.PrivilegeModel;
import com.webarch.model.SysResources;

import java.util.List;

@MyBatisRepository
public interface SysResourcesMapper {

    int deleteByPrimaryKey(Integer resId);

    int insert(SysResources record);

    int insertSelective(SysResources record);

    SysResources selectByPrimaryKey(Integer resId);

    int updateByPrimaryKeySelective(SysResources record);

    int updateByPrimaryKey(SysResources record);

    List<PrivilegeModel> selectResourcePris();

    List<PrivilegeModel> selectResourcePerms();

    List<PrivilegeModel> selectResourceRoles();

}