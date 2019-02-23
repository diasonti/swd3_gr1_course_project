package com.diasonti.descriptiontinder.config.validation.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Size(min = 6, max = 255, message = "password.too.weak")
public @interface StrongPassword {
    String message() default "password.too.weak";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

