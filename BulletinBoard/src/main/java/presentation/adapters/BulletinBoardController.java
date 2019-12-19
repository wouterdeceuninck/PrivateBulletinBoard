package presentation.adapters;

import application.bulletinBoard.BulletinBoard;
import application.bulletinBoard.ExceptionHandlingBulletinBoard;
import application.security.ticket.TicketGrantingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.bulletinboard.BulletinBoardInfoDto;
import shared.bulletinboard.BulletinBoardInterface;
import shared.bulletinboard.dto.GetMessageRequestDTO;
import shared.bulletinboard.dto.PostMessageRequestDTO;
import shared.security.HashFunction;

import java.util.List;

import static shared.utils.DefaultObjectMapper.mapToObject;
import static shared.utils.DefaultObjectMapper.mapToString;

public class BulletinBoardController implements BulletinBoardInterface {

    private final BulletinBoard bulletinBoard;
    private final TicketGrantingService ticketGrantingService;
    private final List<BulletinBoardInfoDto> infoDtoList;

    public BulletinBoardController(TicketGrantingService ticketGrantingService, BulletinBoardInfoDto bulletinBoardInfo, HashFunction hashFunction, List<BulletinBoardInfoDto> infoDtoList) {
        this.bulletinBoard = new ExceptionHandlingBulletinBoard(bulletinBoardInfo.getStart(), bulletinBoardInfo.getEnd(), hashFunction);
        this.ticketGrantingService = ticketGrantingService;
        this.infoDtoList = infoDtoList;
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

    @Override
    public String getBulletinBoardInfo() {
        return mapToString(infoDtoList.toArray());
    }
}
