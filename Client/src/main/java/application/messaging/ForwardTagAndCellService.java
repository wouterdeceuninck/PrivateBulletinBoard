package application.messaging;

import application.exceptions.UnexpectedMappingException;
import application.messaging.forward.SecureGenerator;
import application.messaging.requests.ForwardMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ForwardTagAndCellService {

    private final ObjectMapper objectMapper;

    public ForwardTagAndCellService(SecureGenerator secureGenerator, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String createForwardMessage(String message, int nextIndex, String nextTag) {
        ForwardMessage forwardMessage = new ForwardMessage(nextIndex, nextTag, message);
        return mapToString(forwardMessage);
    }

    private String mapToString(ForwardMessage forwardMessage) {
        try {
            return objectMapper.writeValueAsString(forwardMessage);
        } catch (JsonProcessingException e) {
            throw new UnexpectedMappingException("Could not map forwardMessage to json");
        }
    }
}
