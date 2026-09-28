package com.hello.helloservice.validator;


import com.hello.helloservice.exception.InvalidNameException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1) // Controls execution sequence
public class NotBlankValidator implements NameValidator {
    @Override
    public void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidNameException("Invalid Input");
        }
    }
}