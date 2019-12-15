package presentation.ui.config;

import application.messaging.MessageService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import presentation.ui.config.ClientApplication.StageReadyEvent;
import presentation.ui.controller.MessagingInterfaceController;
import presentation.ui.controller.startup.StartupService;
import presentation.ui.controller.startup.UserInfos;

import java.io.IOException;
import java.util.List;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final ObjectFactory<MessageService> messageServiceObjectFactory;
    private final StartupService startupService;

    @Autowired
    public StageInitializer(ObjectFactory<MessageService> messageService, StartupService startupService) {
        this.messageServiceObjectFactory = messageService;
        this.startupService = startupService;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        List<UserInfos> users = startupService.createUsers();
        users.forEach(this::setFXMLStage);
    }

    private void setFXMLStage(UserInfos userInfos) {
        try {
            MessageService messageService = messageServiceObjectFactory.getObject();
            messageService.addUsers(userInfos);
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MessagingInterface.fxml"));
            fxmlLoader.setController(new MessagingInterfaceController(messageService));
            Parent root1 = fxmlLoader.load();
            stage.setTitle("Messaging interface " + userInfos.getName());
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
