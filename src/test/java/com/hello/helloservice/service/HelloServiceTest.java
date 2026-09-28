package com.hello.helloservice.service;

import com.hello.helloservice.exception.InvalidNameException;
import com.hello.helloservice.validator.NameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HelloServiceTest {

    @Mock
    private NameValidator validator1;

    @Mock
    private NameValidator validator2;

    @Mock
    private NameValidator validator3;

    private HelloService helloService;

    @BeforeEach
    void setUp() {
        helloService = new HelloService(List.of(validator1, validator2, validator3));
    }

    @Test
    @DisplayName("Executes all registered validators and returns formatted title-case greeting")
    void generateGreeting_ValidName_ExecutesValidatorsAndReturnsGreeting() {
        String result = helloService.generateGreeting("ALICE");

        verify(validator1).validate("ALICE");
        verify(validator2).validate("ALICE");
        verify(validator3).validate("ALICE");

        assertEquals("Hello Alice", result);
    }

    @Test
    @DisplayName("Short-circuits at Stage 1 when NotBlankValidator fails")
    void generateGreeting_FirstValidatorFails_ShortCircuitsPipeline() {
        doThrow(new InvalidNameException("Invalid Input"))
                .when(validator1).validate("");

        assertThrows(InvalidNameException.class, () -> helloService.generateGreeting(""));

        verify(validator1).validate("");
        verifyNoInteractions(validator2, validator3);
    }

    @Test
    @DisplayName("Short-circuits at Stage 2 when NameFormatValidator fails")
    void generateGreeting_SecondValidatorFails_ExecutesFirstAndStopsPipeline() {
        String input = "alice bob";
        doThrow(new InvalidNameException("Invalid Input"))
                .when(validator2).validate(input);

        assertThrows(InvalidNameException.class, () -> helloService.generateGreeting(input));

        verify(validator1).validate(input);
        verify(validator2).validate(input);
        verifyNoInteractions(validator3);
    }

    @Test
    @DisplayName("Short-circuits at Stage 3 when AlphabetRangeValidator fails")
    void generateGreeting_ThirdValidatorFails_ExecutesFirstTwoAndStopsPipeline() {
        String input = "Nancy";
        doThrow(new InvalidNameException("Invalid Input"))
                .when(validator3).validate(input);

        assertThrows(InvalidNameException.class, () -> helloService.generateGreeting(input));

        verify(validator1).validate(input);
        verify(validator2).validate(input);
        verify(validator3).validate(input);
    }
}