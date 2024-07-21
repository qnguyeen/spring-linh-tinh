package com.nguyeen.springlinhtinh.validator.EnumValidator;

import com.nguyeen.springlinhtinh.validator.PhoneValidator.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumPatternValidator.class})
public @interface EnumPatternConstraint {
    String name();

    String regexp();

    String message() default "Invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
