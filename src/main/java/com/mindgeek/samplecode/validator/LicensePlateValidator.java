package com.mindgeek.samplecode.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LicensePlateValidator implements ConstraintValidator<ValidateLicensePlate, String> {

    @Override
    public void initialize(ValidateLicensePlate validateLicensePlate) {
    }

    @Override
    public boolean isValid(String licensePlate,
                           ConstraintValidatorContext cxt) {
        return licensePlate != null && licensePlate.matches("^([A-Z])([A-Z])-([A-Z])([A-Z])-([0-9])([0-9])([0-9])([0-9])$")
                && (licensePlate.length() == 10);
    }
}
