package shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import shared.exceptions.UnexpectedMappingException;

public class DefaultObjectMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T mapToObject(Class<T> t, String jsonObject) {
        try {
            return objectMapper.readValue(jsonObject, t);
        } catch (JsonProcessingException e) {
            throw new UnexpectedMappingException("Could not map json to Object");
        }
    }

    public static String mapToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new UnexpectedMappingException("Could not map json to Object");
        }
    }
}
