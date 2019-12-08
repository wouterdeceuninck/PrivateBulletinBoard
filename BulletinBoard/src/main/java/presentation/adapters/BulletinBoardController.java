package presentation.adapters;

import application.bulletinBoard.BulletinBoard;
import shared.BulletinBoardInterface;
import shared.GetMessageRequestDTO;
import shared.PostMessageRequestDTO;

import static shared.utils.DefaultObjectMapper.mapToObject;

public class BulletinBoardController implements BulletinBoardInterface {

    private BulletinBoard bulletinBoard;

    public BulletinBoardController(BulletinBoard bulletinBoard) {
        this.bulletinBoard = bulletinBoard;
    }

    @Override
    public String getMessage(String request) {
        GetMessageRequestDTO getMessageRequestDTO = mapToObject(GetMessageRequestDTO.class, request);
        return getMessageRequestDTO == null ? "" :
                bulletinBoard.get(getMessageRequestDTO.getCell(), getMessageRequestDTO.getTag());
    }

    @Override
    public void postMessage(String request) {
        PostMessageRequestDTO postMessageRequestDTO = mapToObject(PostMessageRequestDTO.class, request);
        if (postMessageRequestDTO == null) return;
        bulletinBoard.add(postMessageRequestDTO.getCell(), postMessageRequestDTO.getTag(), postMessageRequestDTO.getMessage());
    }
}
