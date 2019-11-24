package presentation.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import presentation.security.dto.GetMessageRequestDTO;
import presentation.security.dto.PostMessageRequestDTO;

public class DecryptService {
    private final ObjectMapper objectMapper;

    public DecryptService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public GetMessageRequestDTO decryptGetMessageRequest(String request) throws JsonProcessingException {
        return objectMapper.readValue(request, GetMessageRequestDTO.class);
    }

    public PostMessageRequestDTO decryptPostMessageRequest(String request) throws JsonProcessingException {
        return objectMapper.readValue(request, PostMessageRequestDTO.class);
    }
}
