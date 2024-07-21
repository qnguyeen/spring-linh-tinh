package com.nguyeen.springlinhtinh.validator.GenderValidator;

import com.nguyeen.springlinhtinh.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class GenderValidator implements ConstraintValidator<GenderConstraint, Gender> {
    private Gender[] genders;

    @Override
    public void initialize(GenderConstraint constraint) {
        this.genders = constraint.anyOf();
    }

    @Override
    public boolean isValid(Gender value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(genders).contains(value);
    }
}
