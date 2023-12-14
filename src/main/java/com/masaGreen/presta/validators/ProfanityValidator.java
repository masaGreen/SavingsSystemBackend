package com.masaGreen.presta.validators;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masaGreen.presta.annotations.NotProfane;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProfanityValidator implements ConstraintValidator<NotProfane, String> {
    private final List<String> profanities;

    public ProfanityValidator() {
        this.profanities = getProfanities();
    }


    private List<String> getProfanities() {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/profanities.json");
        try {
            return mapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("Error while loading profanities", e);
            return new ArrayList<>();
        }

    }


    @Override
    public void initialize(NotProfane constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        } else {
            return !profanities.contains(value);
        }
    }
}







