package com.mindgeek.samplecode.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = LicensePlateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateLicensePlate {
    String message() default "Invalid licence plate number. It must match format like 'AB-CD-1234'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
