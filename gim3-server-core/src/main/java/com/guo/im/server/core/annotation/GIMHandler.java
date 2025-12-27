package com.guo.im.server.core.annotation;

import java.lang.annotation.*;


@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GIMHandler {

    int cmd ();

}