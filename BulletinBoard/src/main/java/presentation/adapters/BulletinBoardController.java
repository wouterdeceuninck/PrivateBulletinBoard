package presentation.adapters;

import application.bulletinBoard.BulletinBoard;
import application.security.ticket.TicketGrantingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.BulletinBoardInterface;
import shared.GetMessageRequestDTO;
import shared.PostMessageRequestDTO;

import static shared.utils.DefaultObjectMapper.mapToObject;
import static shared.utils.DefaultObjectMapper.mapToString;

public class BulletinBoardController implements BulletinBoardInterface {

    private final BulletinBoard bulletinBoard;
    private final TicketGrantingService ticketGrantingService;
    private final Logger logger = LoggerFactory.getLogger(BulletinBoardController.class);

    public BulletinBoardController(BulletinBoard bulletinBoard, TicketGrantingService ticketGrantingService) {
        this.bulletinBoard = bulletinBoard;
        this.ticketGrantingService = ticketGrantingService;
    }

    @Override
    public String getMessage(String request) {
        GetMessageRequestDTO getMessageRequestDTO = mapToObject(GetMessageRequestDTO.class, request);
        return getMessageRequestDTO == null ? "" :
                bulletinBoard.get(getMessageRequestDTO.getCell(), getMessageRequestDTO.getTag());
    }

    @Override
    public boolean postMessage(String request) {
        PostMessageRequestDTO postMessageRequestDTO = mapToObject(PostMessageRequestDTO.class, request);
        if (postMessageRequestDTO == null) return false;
        if (!ticketGrantingService.verifySolution(postMessageRequestDTO.getSolvedTicket())) return false;

        bulletinBoard.add(postMessageRequestDTO.getCell(), postMessageRequestDTO.getTag(), postMessageRequestDTO.getMessage());
        return true;
    }

    @Override
    public String getTicket() {
        return mapToString(ticketGrantingService.createTicket());
    }
}
