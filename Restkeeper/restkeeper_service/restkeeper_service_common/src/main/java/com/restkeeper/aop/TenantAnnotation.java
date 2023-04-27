package com.restkeeper.aop;

import java.lang.annotation.*;

/**
 * @作者：xie
 * @时间：2023/4/26 20:15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TenantAnnotation {

}