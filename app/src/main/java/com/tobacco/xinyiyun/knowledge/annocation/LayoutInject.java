package com.tobacco.xinyiyun.knowledge.annocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by YangQiang on 2017/8/7.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutInject {
    int value() default 0;

    boolean translucentStatus() default true;

    boolean lazyLoad() default false;

    boolean rxbus() default false;
}
