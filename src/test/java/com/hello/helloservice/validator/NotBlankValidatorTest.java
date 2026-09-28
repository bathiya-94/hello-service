package com.hello.helloservice.validator;

import com.hello.helloservice.exception.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NotBlankValidatorTest {

    private NotBlankValidator validator;

    @BeforeEach
    void setUp() {
        validator = new NotBlankValidator();
    }

    @Test
    @DisplayName("Valid non-blank name passes validation")
    void testValidName() {
        assertDoesNotThrow(() -> validator.validate("Alice"));
    }

    @Test
    @DisplayName("Null name throws InvalidNameException")
    void testNullName() {
        assertThrows(InvalidNameException.class, () -> validator.validate(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    @DisplayName("Empty or blank names throw InvalidNameException")
    void testBlankNames(String name) {
        assertThrows(InvalidNameException.class, () -> validator.validate(name));
    }
}