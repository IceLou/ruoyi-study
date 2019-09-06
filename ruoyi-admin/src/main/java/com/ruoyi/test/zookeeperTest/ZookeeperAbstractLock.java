package com.ruoyi.test.zookeeperTest;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

/**
 * @author heyalou
 * @date 2019/9/5 11:10
 */
public abstract class ZookeeperAbstractLock implements ExtLock {
    private static final String zookeeperIp = "192.168.75.128:2181";
    static CuratorFramework client;
    public ZookeeperAbstractLock() {
        RetryPolicy retryPolicy = new RetryNTimes(3, 3000);
        client= CuratorFrameworkFactory.builder()
                .connectString(zookeeperIp)
                .sessionTimeoutMs(3000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
    }
    @Override
    public void getLock() {
        try {
            if(tryLock()){
                System.out.println("=====成功获取到锁=====");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unlock() {
        if(client !=null){
            client.close();
            System.out.println("释放锁成功");
        }
    }
    //创建失败 进行等待
    abstract void waitLock() throws Exception;
    abstract boolean tryLock() throws Exception;
}
