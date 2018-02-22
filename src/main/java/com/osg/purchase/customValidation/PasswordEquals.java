package com.osg.purchase.customValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE,ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=PasswordEqualsValidator.class)
@Documented
public @interface PasswordEquals {
    String message() default ""; //error message
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldPasswordNew() default "passwordNew";
    String fieldPasswordConfirm() default "passwordConfirm";

}
