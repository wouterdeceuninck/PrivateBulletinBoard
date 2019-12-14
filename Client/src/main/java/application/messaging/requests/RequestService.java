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

    public PostMessageRequestDTO createPostRequest(UserDto userDto, String message, int nextIndex, String nextTag) {
        String forwardMessage = mapToString(new ForwardMessage(nextIndex, nextTag, message));
        String hashTag = securityService.hashString(userDto.getTag());
        String encryptMessage = securityService.encryptMessage(forwardMessage, userDto.getKeyName());

        return new PostMessageRequestDTO(userDto.getCell(), hashTag, encryptMessage);
    }

    public GetMessageRequestDTO createGetRequest(UserDto userDto) {
        return new GetMessageRequestDTO(userDto.getCell(), userDto.getTag());
    }
}
