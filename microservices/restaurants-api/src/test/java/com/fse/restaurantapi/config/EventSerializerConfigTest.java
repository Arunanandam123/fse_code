package com.fse.restaurantapi.config;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventSerializerConfigTest {

	private EventSerializerConfig eventSerializerConfig;

	@Mock
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		eventSerializerConfig = new EventSerializerConfig(objectMapper);
	}

	@Test
	public void testSerializeEvent() throws JsonProcessingException {

		SampleEvent sampleEvent = new SampleEvent("SampleData");

		Mockito.when(objectMapper.writeValueAsString(sampleEvent)).thenReturn("SerializedData");

		String serialized = eventSerializerConfig.serializeEvent(sampleEvent);

		assertEquals("SerializedData", serialized);
	}

	@Test
	public void testSerializeEvent_WithException() throws JsonProcessingException {

		SampleEvent sampleEvent = new SampleEvent("SampleData");

		Mockito.when(objectMapper.writeValueAsString(sampleEvent)).thenThrow(JsonProcessingException.class);

		assertThrows(RuntimeException.class, () -> eventSerializerConfig.serializeEvent(sampleEvent));
	}

	@Test
	public void testDeserializeEvent() throws JsonProcessingException {
		
		String eventJson = "{\"data\":\"SampleData\"}";

		Class<SampleEvent> eventType = SampleEvent.class;

		SampleEvent expectedEvent = new SampleEvent("SampleData");

		Mockito.when(objectMapper.readValue(eventJson, eventType)).thenReturn(expectedEvent);

		SampleEvent deserialized = eventSerializerConfig.deserializeEvent(eventJson, eventType);

		assertEquals(expectedEvent, deserialized);
	}

	@Test
	public void testDeserializeEvent_WithException() throws JsonProcessingException {

		String eventJson = "{\"data\":\"SampleData\"}";

		Class<SampleEvent> eventType = SampleEvent.class;

		Mockito.when(objectMapper.readValue(eventJson, eventType)).thenThrow(JsonProcessingException.class);

		assertThrows(RuntimeException.class, () -> eventSerializerConfig.deserializeEvent(eventJson, eventType));
	}

	static class SampleEvent {
		private String data;

		public SampleEvent(String data) {
			this.data = data;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}
	}
}
