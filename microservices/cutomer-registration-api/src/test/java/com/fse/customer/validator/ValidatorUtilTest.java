package com.fse.customer.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValidatorUtilTest {

	@Test
	public void testIsValidPhoneNumberValid() {
		assertTrue(ValidatorUtil.isValidPhoneNumber("1234567890"));
		assertTrue(ValidatorUtil.isValidPhoneNumber("9876543210"));
	}
	
	@Test
    public void testIsValidPhoneNumberInvalid() {
        assertFalse(ValidatorUtil.isValidPhoneNumber("1234"));
        assertFalse(ValidatorUtil.isValidPhoneNumber("abcdefghij"));
    }

}
