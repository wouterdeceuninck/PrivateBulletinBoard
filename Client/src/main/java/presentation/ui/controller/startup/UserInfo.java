package presentation.ui.controller.startup;

public class UserInfo {
    private String name;
    private String key;
    private int cell;
    private String tag;

    public UserInfo(String name, String key, int cell, String tag) {
        this.name = name;
        this.key = key;
        this.cell = cell;
        this.tag = tag;
    }

    public UserInfo() {
    }

    public UserInfo(String currentName, UserInfo newUserInfo) {
        this.name = currentName;
        this.key = newUserInfo.key;
        this.cell = newUserInfo.cell;
        this.tag = newUserInfo.tag;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public int getCell() {
        return cell;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", cell=" + cell +
                ", tag='" + tag + '\'' +
                '}';
    }
}
