package application.users;

import application.exceptions.NoSuchUserException;
import application.users.dto.UserDto;

import java.util.Map;

public class UserService {
    private Map<String, UserDto> sendMap;
    private Map<String, UserDto> receiveMap;

    public UserService(Map<String, UserDto> sendMap, Map<String, UserDto> receiveMap) {
        this.sendMap = sendMap;
        this.receiveMap = receiveMap;
    }

    public UserDto getSendUser(String username) {
        UserDto userDto = sendMap.get(username);
        if (userDto == null)
            throw new NoSuchUserException("The user with the username: " + username + " does not exist");
        return userDto;
    }

    public UserDto getReceiveUser(String username) {
        UserDto userDto = receiveMap.get(username);
        if (userDto == null)
            throw new NoSuchUserException("The user with the username: " + username + " does not exist");
        return userDto;
    }

    public void updateSendUser(String username, UserDto userDto) {
        sendMap.put(username, userDto);
    }

    public void updateReceiveUser(String username, UserDto userDto) {
        receiveMap.put(username, userDto);
    }
}
