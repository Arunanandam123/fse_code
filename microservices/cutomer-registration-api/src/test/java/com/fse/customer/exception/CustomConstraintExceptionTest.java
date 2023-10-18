package com.fse.customer.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CustomConstraintExceptionTest {

	@Test
	public void testCustomConstraintException() {
		String exceptionCode = "EX-001";
		String exceptionMessage = "Test Message";
		CustomConstraintException exception = new CustomConstraintException(exceptionCode, exceptionMessage);

		assertEquals(exceptionMessage, exception.getMessage());
		assertEquals(exceptionCode, exception.getExceptionCode());
	}

	@Test
	public void testToString() {
		String exceptionCode = "EX-002";
		String exceptionMessage = "Exception Message";
		CustomConstraintException exception = new CustomConstraintException(exceptionCode, exceptionMessage);

		String expectedToString = "CustomConstraintException{" + "exceptionCode='" + exceptionCode + '\''
				+ ", errorMessage='" + exceptionMessage + '\'' + '}';

		assertEquals(expectedToString, exception.toString());
	}
}
