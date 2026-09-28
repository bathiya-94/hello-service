package com.hello.helloservice.validator;

import com.hello.helloservice.exception.InvalidNameException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Order(2)
public class NameFormatValidator implements NameValidator {

    // Strictly matches names with English letters (A-Z, a-z) only. Rejects numbers, spaces, symbols, and non-ASCII characters.
    private static final Pattern ENGLISH_ALPHABETIC_ONLY = Pattern.compile("^[a-zA-Z]+$");

    @Override
    public void validate(String name) {
        String trimmed = name.trim();

        if (!ENGLISH_ALPHABETIC_ONLY.matcher(trimmed).matches()) {
            throw new InvalidNameException("Invalid Input");
        }
    }
}