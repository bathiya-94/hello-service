package com.hello.helloservice.service;


import com.hello.helloservice.validator.NameValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloService {
    private final List<NameValidator> validators;

    public HelloService(List<NameValidator> validators) {
        this.validators = validators;
    }

    public String generateGreeting(String name) {
        // Run all registered validators
        validators.forEach(validator -> validator.validate(name));

        String trimmedName = name.trim();
        String formattedName = capitalizeFirstLetter(trimmedName);
        return "Hello " + formattedName;
    }

    private String capitalizeFirstLetter(String name) {
        if (name.length() == 1) {
            return name.toUpperCase();
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
    }
}
