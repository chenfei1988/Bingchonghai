package com.tobacco.xinyiyun.knowledge.annocation;

import com.tobacco.xinyiyun.knowledge.view.MultiStateView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by YangQiang on 2017/8/7.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiStateInject {
    int value() default 0;

    int state() default MultiStateView.VIEW_STATE_LOADING;
}
