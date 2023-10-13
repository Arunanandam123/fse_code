package com.fse.restaurantapi.exception;

public class CustomConstraintException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String exceptionCode;
	
	public CustomConstraintException(String exceptionCode, String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
    }

	public String getExceptionCode() {
        return exceptionCode;
    }

    @Override
    public String toString() {
        return "CustomConstraintException{" +
                "exceptionCode='" + exceptionCode + '\'' +
                ", errorMessage='" + getMessage() + '\'' +
                '}';
    }
}
