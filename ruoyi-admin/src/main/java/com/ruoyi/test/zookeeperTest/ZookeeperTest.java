package com.ruoyi.test.zookeeperTest;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.CreateMode;

/**
 * @author heyalou
 * @date 2019/9/5 11:38
 */
public class ZookeeperTest extends ZookeeperAbstractLock {
    final  String nodePath  = "/lock";
    @Override
    void waitLock() throws Exception {
        client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println(curatorEvent.getType()+"==================--------------------");
            }
        });
    }

    @Override
    boolean tryLock() throws Exception {
        String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(nodePath);
        if (result !=null){
            System.out.println("结果如下===================="+result);
            return true;
        }
        return false;
    }
}
