package com.waes.base64compare.domain.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Base64Validation.class)
@Documented
public @interface Base64 {

    String message() default "The value must be a valid base 64 string.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
