package application.users;

import application.exceptions.CorruptedUserState;
import application.exceptions.NoSuchUserException;
import application.users.dto.UserDto;
import shared.HashFunction;

import java.util.Map;

public class UserService {
    private final HashFunction hashFunction;
    private Map<String, UserDto> sendMap;
    private Map<String, UserDto> receiveMap;

    public UserService(Map<String, UserDto> sendMap, Map<String, UserDto> receiveMap, HashFunction hashFunction) {
        this.sendMap = sendMap;
        this.receiveMap = receiveMap;
        this.hashFunction = hashFunction;
    }

    public UserDto getSendUser(String username) {
        UserDto userDto = sendMap.get(username);
        verifyUserDto(username, userDto);
        return userDto;
    }

    public UserDto getReceiveUser(String username) {
        UserDto userDto = receiveMap.get(username);
        verifyUserDto(username, userDto);
        return userDto;
    }

    public void updateSendUser(String username, UserDto userDto) {
        updateUserHash(userDto);
        sendMap.put(username, userDto);
    }

    public void updateReceiveUser(String username, UserDto userDto) {
        updateUserHash(userDto);
        receiveMap.put(username, userDto);
    }

    private void verifyUserDto(String username, UserDto userDto) {
        if (userDto == null)
            throw new NoSuchUserException("The user with the username: " + username + " does not exist");

        if (!verifyHash(userDto))
            throw new CorruptedUserState("The user state is corrupted!");
    }

    private void updateUserHash(UserDto userDto) {
        String hashedValue = hashFunction.hashString(userDto.toString());
        userDto.setHashedValue(hashedValue);
    }

    private boolean verifyHash(UserDto userDto) {
        return hashFunction.hashString(userDto.toString()).equals(userDto.getHashedValue());
    }
}
