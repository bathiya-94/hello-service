package com.hello.helloservice.validator;

import com.hello.helloservice.exception.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FirstLetterValidatorTest {

    private FirstLetterValidator validator;

    @BeforeEach
    void setUp() {
        validator = new FirstLetterValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "alice", "Alice", "bob", "Mary",
            "a", "A", "m", "M", // Single-character exact boundaries
            "  alice  " // Leading/trailing whitespace
    })
    @DisplayName("Names starting with A-M or a-m pass validation")
    void testValidAlphabetRange(String name) {
        assertDoesNotThrow(() -> validator.validate(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "nancy", "Nancy", "zack", "Zack",
            "n", "N", "z", "Z", // Single-character exact boundaries
            "  nancy  " // Whitespace before invalid start char
    })
    @DisplayName("Names starting with N-Z or n-z throw InvalidNameException")
    void testInvalidAlphabetRange(String name) {
        assertThrows(InvalidNameException.class, () -> validator.validate(name));
    }
}
