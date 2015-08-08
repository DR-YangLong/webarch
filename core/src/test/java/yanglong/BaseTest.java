package yanglong;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * package:com.transfar.greentech.test</br>
 * functional describe:测试父类
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/5/27 11:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext*.xml"})
@ActiveProfiles("development")
public abstract class BaseTest {
}
