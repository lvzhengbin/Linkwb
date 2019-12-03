package com.ice.common.utils.workflow;

/**
 * Desc:
 * Created by lvzhengbin
 * Time: 2019-11-26
 */
public interface Worker {

    /**
     * 执行任务
     * @param current 当前节点
     */
    void doWork(Node current);

}
