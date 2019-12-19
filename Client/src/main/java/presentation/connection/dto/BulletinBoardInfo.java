package presentation.connection.dto;

import application.exceptions.NoBulletinBoardFoundForCellException;
import shared.bulletinboard.BulletinBoardInfoDto;

import java.util.ArrayList;
import java.util.List;

public class BulletinBoardInfo {

    private List<BulletinBoardInfoDto> bulletinBoardInfos;

    public BulletinBoardInfo() {
        bulletinBoardInfos = new ArrayList<>();
    }

    public Integer getLocation(int cell) {
        return bulletinBoardInfos.stream()
                .filter(bulletinBoardInfoDto -> containsCell(bulletinBoardInfoDto, cell))
                .findAny()
                .map(BulletinBoardInfoDto::getLocation)
                .orElseThrow(NoBulletinBoardFoundForCellException::new);
    }

    private boolean containsCell(BulletinBoardInfoDto bulletinBoardInfoDto, int cell) {
        return bulletinBoardInfoDto.getStart() <= cell && bulletinBoardInfoDto.getEnd() >= cell;
    }

    public void updateInfo(List<BulletinBoardInfoDto> bulletinBoardInfoDto) {
        bulletinBoardInfos.clear();
        bulletinBoardInfos.addAll(bulletinBoardInfoDto);
    }

}
