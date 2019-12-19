package application.users;

import application.exceptions.NoSuchUserException;
import application.users.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.security.HashFunctionImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

class UserServiceTest {

    private UserService userService = new UserService(new HashMap<>(), new HashMap<>(), new HashFunctionImpl(MessageDigest.getInstance("sha-256")));

    UserServiceTest() throws NoSuchAlgorithmException {
    }

    @Test
    void getNotExistingUser() {
        Assertions.assertThrows(NoSuchUserException.class, () -> userService.getSendUser("user"));
        Assertions.assertThrows(NoSuchUserException.class, () -> userService.getReceiveUser("user"));
    }

    @Test
    void updateSendUser() {
        UserDto userDto = new UserDto("key", 2, "tag");
        userService.updateSendUser("username", userDto);
        UserDto username = userService.getSendUser("username");

        Assertions.assertEquals(userDto, username);

        UserDto userDto1 = new UserDto("key", 3, "newTag");
        userService.updateSendUser("username", userDto1);
        UserDto userDto2 = userService.getSendUser("username");

        Assertions.assertEquals(userDto1, userDto2);
    }

    @Test
    void updateReceiveUser() {
        UserDto userDto = new UserDto("key", 2, "tag");
        userService.updateReceiveUser("username", userDto);

        UserDto username = userService.getReceiveUser("username");
        Assertions.assertEquals(userDto, username);
    }
}