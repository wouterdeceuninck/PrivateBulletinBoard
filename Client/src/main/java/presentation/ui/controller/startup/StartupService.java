package presentation.ui.controller.startup;

import application.messaging.forward.SecureGenerator;
import application.security.keys.KeyService;
import application.security.utils.DefaultKeyEncoder;
import org.apache.commons.codec.binary.StringUtils;
import shared.utils.DefaultObjectMapper;

import javax.crypto.SecretKey;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StartupService {

    private final KeyService keyService;
    private final SecureGenerator secureGenerator;
    private final int amountOfUsers;

    public StartupService(SecureGenerator secureGenerator, int amountOfUsers) {
        this.secureGenerator = secureGenerator;
        keyService = new KeyService(null);
        this.amountOfUsers = amountOfUsers;
    }

    public List<UserInfos> createUsers() {
        List<String> names = readNames();
        return createUserInfos(names, amountOfUsers);
    }

    private List<String> readNames() {
        return Arrays.asList(DefaultObjectMapper.mapToObject(String[].class, readFromFile()));
    }

    private List<UserInfos> createUserInfos(List<String> names, int amount) {
        List<UserInfos> userInfos = names.stream()
                .limit(amount)
                .map(UserInfos::new)
                .collect(Collectors.toList());

        userInfos.forEach(selectedName -> createSentEntryForEveryUser(userInfos, selectedName));
        return userInfos;
    }

    private void createSentEntryForEveryUser(List<UserInfos> selectedNames, UserInfos currentUserInfo) {
        selectedNames.stream()
                .filter(selectedUserInfo -> !selectedUserInfo.getName().equals(currentUserInfo.getName()))
                .forEach(selectedUserInfo -> updateUserInfos(currentUserInfo, selectedUserInfo));
    }

    private void updateUserInfos(UserInfos currentUserInfo, UserInfos selectedUserInfo) {
        UserInfo newUserInfo = createNewUserInfo(selectedUserInfo.getName());
        currentUserInfo.addSendUser(newUserInfo);
        selectedUserInfo.addReceiveUser(createReceiveEntry(currentUserInfo, newUserInfo));
    }

    private UserInfo createReceiveEntry(UserInfos currentUserInfo, UserInfo newUserInfo) {
        return new UserInfo(currentUserInfo.getName(), newUserInfo);
    }

    private UserInfo createNewUserInfo(String name) {
        SecretKey secretKey = keyService.generateSecretKey();
        String tag = secureGenerator.generateTag();
        int nextIndex = secureGenerator.generateCellIndex();
        return new UserInfo(name, DefaultKeyEncoder.encodeSecretKey(secretKey), nextIndex, tag);
    }

    private String readFromFile() {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("/Users/wdeceuninck/IdeaProjects/PrivateBulletinBoard/Client/src/main/resources/startupScript.json"));
            byte[] bytes = bufferedInputStream.readAllBytes();
            return StringUtils.newStringUtf8(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
