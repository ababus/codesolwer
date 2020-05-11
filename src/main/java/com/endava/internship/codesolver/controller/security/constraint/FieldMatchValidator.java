package com.endava.internship.codesolver.controller.security.constraint;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private static final Logger logger = LoggerFactory.getLogger(FieldMatchValidator.class);

    private String firstValue;

    private String secondValue;


    public void initialize(final FieldMatch constraintAnnotation) {
        firstValue = constraintAnnotation.first();
        secondValue = constraintAnnotation.second();
    }


    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstValue);
            final Object secondObj = BeanUtils.getProperty(value, secondValue);
            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception ex) {
            logger.info(ex.getMessage());
        }
        return true;
    }

}