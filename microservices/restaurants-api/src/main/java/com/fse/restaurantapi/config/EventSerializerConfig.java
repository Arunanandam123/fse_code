package com.fse.restaurantapi.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class EventSerializerConfig {

    private final ObjectMapper objectMapper;

    public EventSerializerConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String serializeEvent(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing event to JSON", e);
        }
    }

    public <T> T deserializeEvent(String eventJson, Class<T> eventType) {
        try {
            return objectMapper.readValue(eventJson, eventType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing JSON to event", e);
        }
    }
}
