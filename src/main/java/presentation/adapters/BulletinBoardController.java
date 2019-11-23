package presentation.adapters;

import application.bulletinBoard.BulletinBoard;
import application.bulletinBoard.ExceptionHandlingBulletinBoard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import presentation.ports.BulletinBoardInterface;
import presentation.security.DecryptService;
import presentation.security.HashFunctionImpl;
import presentation.security.dto.GetMessageRequestDTO;
import presentation.security.dto.PostMessageRequestDTO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BulletinBoardController implements BulletinBoardInterface {

    private ExceptionHandlingBulletinBoard bulletinBoard;
    private DecryptService decryptService;

    public BulletinBoardController() throws NoSuchAlgorithmException {
        this.bulletinBoard = new ExceptionHandlingBulletinBoard(5, new HashFunctionImpl(MessageDigest.getInstance("sha-256")));
        decryptService = new DecryptService(new ObjectMapper());
    }

    public String getMessage(String request) {
        GetMessageRequestDTO getMessageRequestDTO = decryptGetMessage(request);
        return getMessageRequestDTO == null ? "" :
                bulletinBoard.get(getMessageRequestDTO.getCell(), getMessageRequestDTO.getTag());
    }

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
