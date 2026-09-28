package com.hello.helloservice.validator;


import com.hello.helloservice.exception.InvalidNameException;

public interface NameValidator {
    void validate(String name) throws InvalidNameException;
}
