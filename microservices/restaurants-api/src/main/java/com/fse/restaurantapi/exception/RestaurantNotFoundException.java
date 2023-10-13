package com.fse.restaurantapi.exception;

public class RestaurantNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String exceptionCode;
	
	public RestaurantNotFoundException(String exceptionCode, String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
    }

	public String getExceptionCode() {
        return exceptionCode;
    }

    @Override
    public String toString() {
        return "RestaurantNotFoundException{" +
                "exceptionCode='" + exceptionCode + '\'' +
                ", errorMessage='" + getMessage() + '\'' +
                '}';
    }
}
