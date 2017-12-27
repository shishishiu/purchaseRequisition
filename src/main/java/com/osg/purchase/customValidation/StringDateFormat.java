package com.osg.purchase.customValidation;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=StringDateFormatValidator.class)
@Documented
public @interface StringDateFormat {
    String message() default ""; //error message
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String pattern() default "";

}
