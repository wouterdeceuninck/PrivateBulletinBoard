package application.messaging;

import application.exceptions.FailedToSolveTicketException;
import application.messaging.requests.ForwardMessage;
import application.security.SecurityService;
import application.security.ticket.TicketSolver;
import application.users.UserService;
import application.users.dto.UserDto;
import presentation.connection.RemoteBulletinBoard;
import presentation.ui.controller.startup.UserInfos;
import shared.bulletinboard.dto.GetMessageRequestDTO;
import shared.bulletinboard.dto.PostMessageRequestDTO;
import shared.ticket.Ticket;
import shared.ticket.TicketSolution;

import java.util.List;

import static shared.utils.DefaultObjectMapper.mapToObject;
import static shared.utils.DefaultObjectMapper.mapToString;

public class MessageService {
    private final SecurityService securityService;
    private final UserService userService;
    private final TicketSolver ticketSolver;
    private final RemoteBulletinBoard remoteBulletinBoard;

    public MessageService(RemoteBulletinBoard remoteBulletinBoard,
                          SecurityService securityService,
                          UserService userService,
                          TicketSolver ticketSolver) {
        this.remoteBulletinBoard = remoteBulletinBoard;
        this.securityService = securityService;
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
        PostMessageRequestDTO postRequest = createPostMessageRequestDTO(message, nextCellIndex, nextTag, sendUser);
        Ticket ticket = mapToObject(Ticket.class, requestTicket(postRequest));
        TicketSolution solution = ticketSolver.solveTicket(ticket);
        postRequest.setSolution(solution);

        boolean isSuccess = remoteBulletinBoard.postMessage(postRequest);
        if (!isSuccess) throw new FailedToSolveTicketException();
    }

    private PostMessageRequestDTO createPostMessageRequestDTO(String message, int nextCellIndex, String nextTag, UserDto sendUser) {
        String forwardMessage = mapToString(new ForwardMessage(nextCellIndex, nextTag, message));
        String hashTag = securityService.hashString(sendUser.getTag());
        String encryptMessage = securityService.encryptMessage(forwardMessage, sendUser.getKeyName());

        return new PostMessageRequestDTO(sendUser.getCell(), hashTag, encryptMessage);
    }

    private String requestTicket(PostMessageRequestDTO postRequest) {
        return remoteBulletinBoard.getTicket(postRequest);
    }

    private String getMessageFromBulletinBoard(UserDto receiveUser) {
        GetMessageRequestDTO getRequest = new GetMessageRequestDTO(receiveUser.getCell(), receiveUser.getTag());
        return remoteBulletinBoard.getMessage(getRequest);
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
