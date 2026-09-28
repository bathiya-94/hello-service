package com.hello.helloservice.validator;

import com.hello.helloservice.exception.InvalidNameException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class FirstLetterValidator implements NameValidator {
    @Override
    public void validate(String name) {
        String trimmed = name.trim();
        char firstChar = Character.toLowerCase(trimmed.charAt(0));

        if (firstChar < 'a' || firstChar > 'm') {
            throw new InvalidNameException("Invalid Input");
        }
    }
}