package shared;

public class PostMessageRequestDTO {
    private int cell;
    private String tag;
    private String message;

    public PostMessageRequestDTO() {
    }

    public PostMessageRequestDTO(int cell, String tag, String message) {
        this.cell = cell;
        this.tag = tag;
        this.message = message;
    }

    public int getCell() {
        return cell;
    }

    public String getTag() {
        return tag;
    }

    public String getMessage() {
        return message;
    }
}
