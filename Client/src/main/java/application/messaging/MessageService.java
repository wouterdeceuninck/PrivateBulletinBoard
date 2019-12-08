package application.messaging;

import application.messaging.requests.ForwardMessage;
import application.messaging.requests.RequestService;
import application.security.SecurityService;
import application.users.UserService;
import application.users.dto.UserDto;
import shared.BulletinBoardInterface;

import static shared.utils.DefaultObjectMapper.mapToObject;

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

        postMessageToBulletinBoard(message, nextCellIndex, nextTag, sendUser);
        updateSenderState(receiver, nextCellIndex, nextTag, sendUser);
    }

    String getMessage(String receiver) {
        UserDto receiveUser = userService.getReceiveUser(receiver);
        String encryptMessage = getMessageFromBulletinBoard(receiveUser);
        String message = securityService.decryptMessage(encryptMessage, receiveUser.getKeyName());

        updateReceiverState(receiver, receiveUser, mapToObject(ForwardMessage.class, message));
        return message;
    }

    private void postMessageToBulletinBoard(String message, int nextCellIndex, String nextTag, UserDto sendUser) {
        String postRequest = requestService.createPostRequest(sendUser, message, nextCellIndex, nextTag);
        bulletinBoard.postMessage(postRequest);
    }

    private void updateSenderState(String receiver, int nextCellIndex, String nextTag, UserDto sendUser) {
        UserDto userDto = new UserDto(sendUser.getKeyName(), nextCellIndex, nextTag);
        userService.updateSendUser(receiver, userDto);
        securityService.updateSecurityKey(sendUser.getKeyName());
    }

    private String getMessageFromBulletinBoard(UserDto receiveUser) {
        String getRequest = requestService.createGetRequest(receiveUser);
        return bulletinBoard.getMessage(getRequest);
    }

    private void updateReceiverState(String receiver, UserDto receiveUser, ForwardMessage forwardMessage) {
        UserDto userDto = new UserDto(receiveUser.getKeyName(), forwardMessage.getNextIndex(), forwardMessage.getNextTag());
        userService.updateReceiveUser(receiver, userDto);
        securityService.updateSecurityKey(receiveUser.getKeyName());
    }
}
