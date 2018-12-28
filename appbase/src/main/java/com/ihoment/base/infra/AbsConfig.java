package com.ihoment.base.infra;

/**
 * Created by xieyingwu on 2018/1/19.
 * 抽象定义Config配置
 */

public abstract class AbsConfig {
    protected AbsConfig() {
        initDefaultAttrs();
    }

    /**
     * 用于构建默认值属性
     */
    protected abstract void initDefaultAttrs();
}
