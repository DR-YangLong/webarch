package yanglong;


import com.webarch.common.shiro.dynamic.JdbcPermissionDao;
import com.webarch.dao.SysResourcesMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


public class AppTest extends BaseTest{
    @Autowired
    private SysResourcesMapper resourcesMapper;
    @Autowired
    private JdbcPermissionDao jdbcPermissionDao;

    @Test
    public void testJdbc(){
        Map<String,String> privileges=jdbcPermissionDao.findDefinitionsMap();
        Assert.assertNotNull(privileges);
    }
}
