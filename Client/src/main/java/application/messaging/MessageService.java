package application.messaging;

import application.messaging.requests.ForwardMessage;
import application.messaging.requests.RequestService;
import application.security.SecurityService;
import application.users.UserService;
import application.users.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import shared.BulletinBoardInterface;

class MessageService {
    private final SecurityService securityService;
    private final BulletinBoardInterface bulletinBoard;
    private final RequestService requestService;
    private final UserService userService;

    MessageService(BulletinBoardInterface bulletinBoard,
                          RequestService requestService,
                          SecurityService securityService,
                          UserService userService) {
        this.bulletinBoard = bulletinBoard;
        this.securityService = securityService;
        this.requestService = requestService;
        this.userService = userService;
    }

    void sendMessage(String message, String receiver) {
        int nextCellIndex = securityService.generateCellIndex();
        String nextTag = securityService.generateTag();
        UserDto sendUser = userService.getSendUser(receiver);

        String postRequest = requestService.createPostRequest(sendUser, message, nextCellIndex, nextTag);
        bulletinBoard.postMessage(postRequest);

        UserDto userDto = new UserDto(sendUser.getKeyName(), nextCellIndex, nextTag);
        userService.updateSendUser(receiver, userDto);
    }

    String getMessage(String receiver) {

        UserDto receiveUser = userService.getReceiveUser(receiver);
        String getRequest = requestService.createGetRequest(receiveUser);
        String encryptMessage = bulletinBoard.getMessage(getRequest);

        String message = securityService.decryptMessage(encryptMessage, receiveUser.getKeyName());
        ForwardMessage forwardMessage = readValue(message);
        userService.updateReceiveUser(receiver, new UserDto(receiveUser.getKeyName(), forwardMessage.getNextIndex(), forwardMessage.getNextTag()));
        return message;
    }

    private ForwardMessage readValue(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(message, ForwardMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
