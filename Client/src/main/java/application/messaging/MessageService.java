package application.messaging;

import application.messaging.requests.ForwardMessage;
import application.messaging.requests.RequestService;
import application.security.SecurityService;
import application.security.ticket.TicketSolver;
import application.users.UserService;
import application.users.dto.UserDto;
import org.springframework.beans.factory.ObjectFactory;
import presentation.ui.controller.startup.UserInfos;
import shared.BulletinBoardInterface;
import shared.GetMessageRequestDTO;
import shared.PostMessageRequestDTO;
import shared.ticket.Ticket;
import shared.ticket.TicketSolution;

import java.util.List;

import static shared.utils.DefaultObjectMapper.mapToObject;
import static shared.utils.DefaultObjectMapper.mapToString;

public class MessageService {
    private final SecurityService securityService;
    private final BulletinBoardInterface bulletinBoardSend;
    private final BulletinBoardInterface bulletinBoardReceive;
    private final RequestService requestService;
    private final UserService userService;
    private final TicketSolver ticketSolver;

    public MessageService(ObjectFactory<BulletinBoardInterface> bulletinBoard,
                   RequestService requestService,
                   SecurityService securityService,
                   UserService userService,
                   TicketSolver ticketSolver) {
        this.bulletinBoardSend = bulletinBoard.getObject();
        this.bulletinBoardReceive = bulletinBoard.getObject();
        this.securityService = securityService;
        this.requestService = requestService;
        this.userService = userService;
        this.ticketSolver = ticketSolver;
    }

    public void sendMessage(String message, String receiver) {
        int nextCellIndex = securityService.generateCellIndex();
        String nextTag = securityService.generateTag();
        UserDto sendUser = userService.getSendUser(receiver);

        postMessageToBulletinBoard(message, nextCellIndex, nextTag, sendUser);
        updateSenderState(receiver, nextCellIndex, nextTag, sendUser);
    }

    public String getMessage(String receiver) {
        UserDto receiveUser = userService.getReceiveUser(receiver);
        String encryptMessage = getMessageFromBulletinBoard(receiveUser);
        if (encryptMessage.equals("")) return "";

        String message = securityService.decryptMessage(encryptMessage, receiveUser.getKeyName());
        ForwardMessage forwardMessage = mapToObject(ForwardMessage.class, message);
        updateReceiverState(receiver, receiveUser, forwardMessage);
        return forwardMessage.getMessage();
    }

    public void addUsers(UserInfos userInfos) {
        userService.addUsers(userInfos);
        securityService.addKey(userInfos);
    }

    private void postMessageToBulletinBoard(String message, int nextCellIndex, String nextTag, UserDto sendUser) {
        PostMessageRequestDTO postRequest = requestService.createPostRequest(sendUser, message, nextCellIndex, nextTag);
        Ticket ticket = mapToObject(Ticket.class, requestTicket());
        TicketSolution solution = ticketSolver.solveTicket(ticket);
        postRequest.setSolution(solution);

        bulletinBoardSend.postMessage(mapToString(postRequest));
    }

    private String requestTicket() {
        return bulletinBoardSend.getTicket();
    }

    private String getMessageFromBulletinBoard(UserDto receiveUser) {
        GetMessageRequestDTO getRequest = requestService.createGetRequest(receiveUser);
        return bulletinBoardReceive.getMessage(mapToString(getRequest));
    }

    private void updateSenderState(String receiver, int nextCellIndex, String nextTag, UserDto sendUser) {
        UserDto userDto = new UserDto(sendUser.getKeyName(), nextCellIndex, nextTag);
        userService.updateSendUser(receiver, userDto);
        securityService.updateSecurityKey("send_" + sendUser.getKeyName());
    }

    private void updateReceiverState(String receiver, UserDto receiveUser, ForwardMessage forwardMessage) {
        UserDto userDto = new UserDto(receiveUser.getKeyName(), forwardMessage.getNextIndex(), forwardMessage.getNextTag());
        userService.updateReceiveUser(receiver, userDto);
        securityService.updateSecurityKey("receive_" + receiveUser.getKeyName());
    }

    public List<String> getUsers() {
        return userService.getUsers();
    }
}
