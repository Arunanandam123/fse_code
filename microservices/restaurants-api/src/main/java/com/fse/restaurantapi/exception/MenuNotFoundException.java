package com.fse.restaurantapi.exception;

public class MenuNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String exceptionCode;
	
	public MenuNotFoundException(String exceptionCode, String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
    }

	public String getExceptionCode() {
        return exceptionCode;
    }

    @Override
    public String toString() {
        return "MenuNotFoundException{" +
                "exceptionCode='" + exceptionCode + '\'' +
                ", errorMessage='" + getMessage() + '\'' +
                '}';
    }
}
