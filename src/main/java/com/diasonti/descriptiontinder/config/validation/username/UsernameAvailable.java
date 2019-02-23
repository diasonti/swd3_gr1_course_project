package com.diasonti.descriptiontinder.config.validation.username;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameAvailableValidator.class)
public @interface UsernameAvailable {
    String message() default "username.is.taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

