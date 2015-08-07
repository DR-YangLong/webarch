package com.webarch.core;

import com.webarch.common.shiro.dynamic.JdbcPermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.webarch.dao.SysResPermMapper;
import com.webarch.dao.SysResRoleMapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * package: com.webarch.core <br/>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/6 19:27
 */
@Component
public class JdbcPermissionDaoImpl implements JdbcPermissionDao{
    @Autowired
    private SysResPermMapper resPermMapper;
    @Autowired
    private SysResRoleMapper resRoleMapper;
    @Override
    public LinkedHashMap<String, String> generateDefinitions() {
        return null;
    }

    @Override
    public Map<String, String> findDefinitionsMap() {
        return null;
    }
}
