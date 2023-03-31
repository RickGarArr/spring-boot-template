package com.rga.gradesubmission.domain.validation.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurpConstraintValidator implements ConstraintValidator<Curp, String> {

    private boolean verifyLength;
    private static final short CURP_LENGTH = 18;

    @Override
    public void initialize(Curp constraintAnnotation) {
        this.verifyLength = constraintAnnotation.verifyLength();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (verifyLength && !(value.length() == CURP_LENGTH))
            return false;
        Pattern pattern = Pattern.compile("^[A-Z]{1}[AEIOU]{1}[A-Z]{2}"
                + "[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" + "[HM]{1}"
                + "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)"
                + "[B-DF-HJ-NP-TV-Z]{3}" + "[0-9A-Z]{1}" + "[0-9]{1}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
