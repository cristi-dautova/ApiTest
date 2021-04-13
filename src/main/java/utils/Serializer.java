package utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class Serializer {

    private static ObjectMapper mapper;

    public static <T> T deserializeJson(String jsonInput, Class<T> clazz) throws IOException {
        mapper = new ObjectMapper();
        return mapper.readValue(jsonInput, clazz);
    }

    public static <T> String serializeEntitiesToJson(T value) throws IOException {
        mapper = new ObjectMapper();
        return mapper.writeValueAsString(value);
    }

}
