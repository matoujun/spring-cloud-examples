package org.matoujun.cloud.api.dao;

import org.matoujun.cloud.api.ApiMain;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author matoujun

 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiMain.class)
public class NativeDaoTest {
    @Autowired
    private NativeDao nativeDao;


}
