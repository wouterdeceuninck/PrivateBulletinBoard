package application.messaging.requests;

import application.security.SecurityService;
import application.users.dto.UserDto;
import shared.GetMessageRequestDTO;
import shared.PostMessageRequestDTO;

import static shared.utils.DefaultObjectMapper.mapToString;

public class RequestService {
    private final SecurityService securityService;

    public RequestService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public String createPostRequest(UserDto userDto, String message, int nextIndex, String nextTag) {
        String forwardMessage = mapToString(new ForwardMessage(nextIndex, nextTag, message));
        String hashTag = securityService.hashString(userDto.getTag());
        String encryptMessage = securityService.encryptMessage(forwardMessage, userDto.getKeyName());

        PostMessageRequestDTO postRequest = new PostMessageRequestDTO(userDto.getCell(), hashTag, encryptMessage);
        return mapToString(postRequest);
    }

    public String createGetRequest(UserDto userDto) {
        GetMessageRequestDTO getRequest = new GetMessageRequestDTO(userDto.getCell(), userDto.getTag());
        return mapToString(getRequest);
    }
}
