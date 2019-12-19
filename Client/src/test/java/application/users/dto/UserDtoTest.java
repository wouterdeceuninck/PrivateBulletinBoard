package application.users.dto;

import org.junit.jupiter.api.Test;
import shared.security.HashFunctionImpl;
import shared.utils.DefaultObjectMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void name() throws NoSuchAlgorithmException {
        UserDto userDto = new UserDto("keyName", 2, "tag");
        userDto.setHashedValue(new HashFunctionImpl(MessageDigest.getInstance("sha-256")).hashString(userDto.toString()));
        String s = DefaultObjectMapper.mapToString(userDto);

        System.out.println(s);
    }
}