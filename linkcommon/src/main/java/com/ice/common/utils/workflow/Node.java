package com.ice.common.utils.workflow;

/**
 * Desc:
 * Created by lvzhengbin
 * Time: 2019-11-26
 */
public interface Node {

    /**
     * 节点id
     * @return 当前节点id
     */
    int getId();

    /**
     * 节点完成时调用，触发下一个节点任务执行
     */
    void onCompleted();
}
