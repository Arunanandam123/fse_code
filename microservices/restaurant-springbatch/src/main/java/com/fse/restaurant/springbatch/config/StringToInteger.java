package com.fse.restaurant.springbatch.config;

import org.springframework.core.convert.converter.Converter;

public class StringToInteger implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        try {
            return Integer.parseInt(source);
        } catch (NumberFormatException e) {
            // Handle conversion errors as needed
            return null;
        }
    }
}

