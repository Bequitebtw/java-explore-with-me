package ru.practicum.ewm.service.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.practicum.ewm.service.validator.StrictEmailValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrictEmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrictEmail {
    String message() default "Invalid email: local-part must be ≤ 64 chars, each domain label ≤ 63 chars.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}