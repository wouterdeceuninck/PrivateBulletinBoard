package application.messaging.requests;

import application.exceptions.UnexpectedMappingException;
import application.messaging.ForwardTagAndCellService;
import application.security.SecurityService;
import application.users.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import shared.GetMessageRequestDTO;
import shared.PostMessageRequestDTO;

public class RequestService {
    private final ObjectMapper objectMapper;
    private final ForwardTagAndCellService forwardTagAndCellService;
    private final SecurityService securityService;

    public RequestService(ObjectMapper objectMapper, ForwardTagAndCellService forwardTagAndCellService, SecurityService securityService) {
        this.objectMapper = objectMapper;
        this.forwardTagAndCellService = forwardTagAndCellService;
        this.securityService = securityService;
    }

    public String createPostRequest(UserDto userDto, String message, int nextIndex, String nextTag) {
        String forwardMessage = forwardTagAndCellService.createForwardMessage(message, nextIndex, nextTag);
        String hashTag = securityService.hashString(userDto.getTag());
        String encryptMessage = securityService.encryptMessage(forwardMessage, userDto.getKeyName());

        PostMessageRequestDTO postRequest = new PostMessageRequestDTO(userDto.getCell(), hashTag, encryptMessage);
        return mapToString(postRequest);
    }

    public String createGetRequest(UserDto userDto) {
        GetMessageRequestDTO getRequest = new GetMessageRequestDTO(userDto.getCell(), userDto.getTag());
        return mapToString(getRequest);
    }


    private String mapToString(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new UnexpectedMappingException("Mapping of bulletinBoard request failed!");
        }
    }
}
