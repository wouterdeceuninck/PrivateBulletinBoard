package application.users.dto;

public class UserDto {
    private String keyName;
    private int cell;
    private String tag;
    private String hashedValue;

    public UserDto(String keyName, int cell, String tag) {
        this.keyName = keyName;
        this.cell = cell;
        this.tag = tag;
    }

    public String getKeyName() {
        return keyName;
    }

    public int getCell() {
        return cell;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "keyName='" + keyName + '\'' +
                ", cell=" + cell +
                ", tag='" + tag + '\'' +
                '}';
    }

    public void setHashedValue(String hashedValue) {
        this.hashedValue = hashedValue;
    }

    public String getHashedValue() {
        return hashedValue;
    }
}
