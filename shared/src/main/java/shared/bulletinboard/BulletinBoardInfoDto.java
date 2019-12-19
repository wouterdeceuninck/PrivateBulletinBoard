package shared.bulletinboard;

public class BulletinBoardInfoDto {

    private int location;
    private int start;
    private int end;
    private String serviceName;

    public BulletinBoardInfoDto(int location, int start, int end, String serviceName) {
        this.location = location;
        this.start = start;
        this.end = end;
        this.serviceName = serviceName;
    }

    public BulletinBoardInfoDto() {
    }

    public int getLocation() {
        return location;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getServiceName() {
        return serviceName;
    }
}
