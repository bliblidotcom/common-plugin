package com.blibli.oss.common.metadata;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MetaDatas {

  MetaData[] value() default {};

}
