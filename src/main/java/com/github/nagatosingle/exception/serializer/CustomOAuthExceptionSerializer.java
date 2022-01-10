//package com.github.nagatosingle.exception.serializer;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.ser.std.StdSerializer;
//import com.github.nagatosingle.exception.auth.CustomOAuth2Exception;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Map;
//
//public class CustomOAuthExceptionSerializer extends StdSerializer<CustomOAuth2Exception> {
//	public CustomOAuthExceptionSerializer() {
//		super(CustomOAuth2Exception.class);
//	}
//
//
//	@Override
//	public void serialize(CustomOAuth2Exception value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//		jsonGenerator.writeStartObject();
//		jsonGenerator.writeNumberField("code", value.getHttpErrorCode());
//		jsonGenerator.writeBooleanField("status", false);
//		jsonGenerator.writeObjectField("data", null);
//		jsonGenerator.writeObjectField("errors", Arrays.asList(value.getOAuth2ErrorCode(),value.getMessage()));
//		if (value.getAdditionalInformation() != null) {
//			for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
//				String key = entry.getKey();
//				String add = entry.getValue();
//				jsonGenerator.writeStringField(key, add);
//			}
//		}
//		jsonGenerator.writeEndObject();
//	}
//
//}
