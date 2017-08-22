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
public @interface ToolbarInject {

    String value() default "";

    boolean showBack() default false;

    boolean flotage() default false;

    int colorId() default 0;

    int menuId() default 0;
}
