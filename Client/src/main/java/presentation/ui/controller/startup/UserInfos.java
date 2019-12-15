package presentation.ui.controller.startup;

import java.util.ArrayList;
import java.util.List;

public class UserInfos {
    private List<UserInfo> userInfoListSend = new ArrayList<>();
    private List<UserInfo> userInfoListReceive = new ArrayList<>();
    private String name;

    public UserInfos(String name) {
        this.name = name;
    }

    public List<UserInfo> getUserInfoListSend() {
        return userInfoListSend;
    }

    public List<UserInfo> getUserInfoListReceive() {
        return userInfoListReceive;
    }

    public String getName() {
        return name;
    }

    public void addSendUser(UserInfo newUserInfo) {
        this.userInfoListSend.add(newUserInfo);
    }

    public void addReceiveUser(UserInfo receiveEntry) {
        this.userInfoListReceive.add(receiveEntry);
    }
}
