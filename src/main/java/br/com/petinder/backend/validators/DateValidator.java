package br.com.petinder.backend.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateValidator implements ConstraintValidator<OfLegalAge, Date> {

    int minAge;

    @Override
    public void initialize(OfLegalAge constraintAnnotation) {
        this.minAge = constraintAnnotation.currentAge();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) return false;
        return date.before(Date.from(LocalDateTime.now().minusYears(this.minAge).atZone(ZoneId.systemDefault()).toInstant()));
    }
}