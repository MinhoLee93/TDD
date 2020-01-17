package com.pratice.isbnvalidation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateISBNTest {

    @Test
    public void checkValidISBN(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449116");
        assertTrue(result);

        result = validator.checkISBN("0140177396");
        assertTrue(result);
    }

    @Test
    public void checkInvalidISBN(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449117");

        assertFalse(result);
    }

    @Test
    public void nineDigitISBNsAreNotAllowed(){
        ValidateISBN validator = new ValidateISBN();

        assertThrows(NumberFormatException.class, () -> {
            boolean result = validator.checkISBN("123456789");
        });

    }
}