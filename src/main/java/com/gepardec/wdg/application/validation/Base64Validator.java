package com.gepardec.wdg.application.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class Base64Validator implements ConstraintValidator<Base64, String> {
    /**
     * Whitespace regular expression.
     */
    private static final String WHITESPACE_REGEX = "\\s";
    /**
     * Base64 validation regular expression.
     */
    //private static final Pattern BASE64_PATTERN = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Ga-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
    //Bei Padding = 1 mit einem "=" auf A-Z geändert
    
    //Niederschick: Interessant, über das Base64-Padding gelernt zu haben. Anzahl Zeichen durch 3 teilbar, sonst Padding mit = bzw. ==. War mit neu.
    //Im vorliegenden Fall war im Pattener das A-G auf A-Z zu ändern. Wegen dem u am Ende des Strings bei Padding = 1
    private static final Pattern BASE64_PATTERN = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");

    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        final String sanitized = value.replaceAll(WHITESPACE_REGEX, "");
        return BASE64_PATTERN.matcher(sanitized).matches();
    }
}
