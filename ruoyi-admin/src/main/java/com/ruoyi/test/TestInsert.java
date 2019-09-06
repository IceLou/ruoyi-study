package com.ruoyi.test;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysRoleDept;
import com.ruoyi.system.mapper.SysRoleDeptMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author heyalou
 * @date 2019/8/28 11:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class TestInsert {
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Test
    public void saveDeptBatchTwo() {
        //设置ExecutorType.BATCH原理：把SQL语句发个数据库，数据库预编译好，数据库等待需要运行的参数，接收到参数后一次运行，ExecutorType.BATCH只打印一次SQL语句，多次设置参数步骤，
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            SysRoleDeptMapper deptMapper = session.getMapper(SysRoleDeptMapper.class);
            long start =System.currentTimeMillis();
            List<SysRoleDept> deptList=new ArrayList();
            for (long i = 0; i <10000000; i++) {
                SysRoleDept sysRoleDept=new SysRoleDept(i,i);
                deptList.add(sysRoleDept);
                if(i%500==0){
                    deptMapper.saveDeptBatch(deptList);
                    deptList.clear();
                }
            }
            //long start =System.currentTimeMillis();
            deptMapper.saveDeptBatch(deptList);
            long end =System.currentTimeMillis();
            System.out.println("耗时:"+(end-start)/1000+"秒");
            //BATCH批量耗时 耗时:822
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.commit();
            session.close();
        }
    }

}
