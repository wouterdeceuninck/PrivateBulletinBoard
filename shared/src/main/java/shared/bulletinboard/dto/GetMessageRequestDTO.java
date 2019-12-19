package shared.bulletinboard.dto;

public class GetMessageRequestDTO {
    private int cell;
    private String tag;

    public GetMessageRequestDTO() {
    }

    public GetMessageRequestDTO(int cell, String tag) {
        this.cell = cell;
        this.tag = tag;
    }

    public int getCell() {
        return cell;
    }

    public String getTag() {
        return tag;
    }
}
