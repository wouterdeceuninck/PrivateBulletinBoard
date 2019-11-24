package presentation.adapters;

import application.bulletinBoard.ExceptionHandlingBulletinBoard;
import com.fasterxml.jackson.core.JsonProcessingException;
import presentation.ports.BulletinBoardInterface;
import presentation.security.DecryptService;
import presentation.security.HashFunction;
import presentation.security.dto.GetMessageRequestDTO;
import presentation.security.dto.PostMessageRequestDTO;

public class BulletinBoardController implements BulletinBoardInterface {

    private ExceptionHandlingBulletinBoard bulletinBoard;
    private DecryptService decryptService;

    public BulletinBoardController(int cellSize, HashFunction hashFunction, DecryptService service) {
        this.bulletinBoard = new ExceptionHandlingBulletinBoard(cellSize, hashFunction);
        decryptService = service;
    }

    @Override
    public String getMessage(String request) {
        GetMessageRequestDTO getMessageRequestDTO = decryptGetMessage(request);
        return getMessageRequestDTO == null ? "" :
                bulletinBoard.get(getMessageRequestDTO.getCell(), getMessageRequestDTO.getTag());
    }

    @Override
    public void postMessage(String request) {
        PostMessageRequestDTO postMessageRequestDTO = decryptPostMessage(request);
        if (postMessageRequestDTO == null) return;
        bulletinBoard.add(postMessageRequestDTO.getCell(), postMessageRequestDTO.getTag(), postMessageRequestDTO.getMessage());
    }

    private PostMessageRequestDTO decryptPostMessage(String request) {
        try {
            return decryptService.decryptPostMessageRequest(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private GetMessageRequestDTO decryptGetMessage(String request) {
        try {
            return decryptService.decryptGetMessageRequest(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
