package com.masaGreen.presta.annotations;

import com.masaGreen.presta.validators.ProfanityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProfanityValidator.class)
public @interface NotProfane {
    String message() default "profanity/null not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
