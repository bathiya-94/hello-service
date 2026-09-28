package com.hello.helloservice.validator;

import com.hello.helloservice.exception.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameFormatValidatorTest {

    private NameFormatValidator validator;

    @BeforeEach
    void setUp() {
        validator = new NameFormatValidator();
    }

    @ParameterizedTest
    @DisplayName("Should pass validation for valid single-word English names")
    @ValueSource(strings = {
            "alice",
            "Bob",
            "CHARLIE",
            "aLICE",
            "m",
            "Zack"
    })
    void validate_validEnglishNames_doesNotThrowException(String name) {
        assertDoesNotThrow(() -> validator.validate(name));
    }

    @ParameterizedTest
    @DisplayName("Should throw InvalidNameException when input contains numbers")
    @ValueSource(strings = {
            "alice1",
            "123alice",
            "al1ce",
            "999"
    })
    void validate_namesWithNumbers_throwsInvalidNameException(String name) {
        assertThrows(InvalidNameException.class, () -> validator.validate(name));
    }

    @ParameterizedTest
    @DisplayName("Should throw InvalidNameException when input contains spaces or multiple words")
    @ValueSource(strings = {
            "alice bob",
            " alice bob ",
            "a b",
            "Mary Ann"
    })
    void validate_multiWordNames_throwsInvalidNameException(String name) {
        assertThrows(InvalidNameException.class, () -> validator.validate(name));
    }

    @ParameterizedTest
    @DisplayName("Should throw InvalidNameException when input contains special characters or punctuation")
    @ValueSource(strings = {
            "alice-bob",
            "alice_bob",
            "!alice",
            "alice$",
            "alice.bob",
            "alice@email"
    })
    void validate_specialCharacters_throwsInvalidNameException(String name) {
        assertThrows(InvalidNameException.class, () -> validator.validate(name));
    }

    @ParameterizedTest
    @DisplayName("Should throw InvalidNameException when input contains non-ASCII or accented characters")
    @ValueSource(strings = {
            "Álvaro",
            "Élodie",
            "ñandú",
            "München",
            "😀alice"
    })
    void validate_nonAsciiCharacters_throwsInvalidNameException(String name) {
        assertThrows(InvalidNameException.class, () -> validator.validate(name));
    }
}