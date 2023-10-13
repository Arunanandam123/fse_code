package com.fse.customer.validator;

import java.util.regex.Pattern;

public class ValidatorUtil {
	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[0-9]{10}$");

    public static boolean isValidPhoneNumber(String phoneNumber) {        
        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }
}
