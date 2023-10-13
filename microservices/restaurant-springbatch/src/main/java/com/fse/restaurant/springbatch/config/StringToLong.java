package com.fse.restaurant.springbatch.config;

import org.springframework.core.convert.converter.Converter;

public class StringToLong implements Converter<String, Long> {
    @Override
    public Long convert(String source) {
        try {
            return Long.parseLong(source);
        } catch (NumberFormatException e) {
            // Handle conversion errors as needed
            return null;
        }
    }
}

