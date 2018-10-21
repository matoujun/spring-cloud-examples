package org.matoujun.cloud.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author matoujun

 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final ObjectMapper stringMapper = new ObjectMapper();

    private JsonUtil() {

    }

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        stringMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        stringMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        stringMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        stringMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
    }


    public static String object2Json(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T jsonStr2Object(String jsonStr, Class<T> clazz) throws JsonParseException,
            JsonMappingException, IOException {
        return mapper.readValue(jsonStr, clazz);
    }

    public static JsonNode jsonStr2JsonNode(String jsonStr) throws JsonProcessingException,
            IOException {
        return mapper.readTree(jsonStr);
    }

    public static <T> T jsonStr2TypeReference(String jsonStr, TypeReference<T> valueTypeRef)
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(jsonStr, valueTypeRef);
    }

    public static String object2JsonUseStringValue(Object obj) throws JsonProcessingException {
        return stringMapper.writeValueAsString(obj);
    }
}
