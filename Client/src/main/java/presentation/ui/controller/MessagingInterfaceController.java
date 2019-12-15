package presentation.ui.controller;

import application.messaging.MessageService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MessagingInterfaceController implements Initializable {

    @FXML
    AnchorPane namesListPane;

    @FXML
    TextArea messageBoard;

    @FXML
    TextArea enterArea;

    private ListView<String> namesList;
    private String selectedUser;
    private ObservableList namesListData = FXCollections.observableArrayList();

    MessageService messageService;
    private List<String> names;
    private final ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public MessagingInterfaceController(MessageService messageService) {
        this.messageService = messageService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::updateMessages, 1000, 3000, TimeUnit.MILLISECONDS);
    }

    private void updateMessages() {
        if (selectedUser != null) {
            String message = messageService.getMessage(selectedUser);
            if (!message.equals("")) {
                messageBoard.appendText("\n" + selectedUser + ": " + message);
            }
        }
    }

    @FXML
    public void sendMessage() {
        if (selectedUser != null && !enterArea.getText().equals("")) {
            Platform.runLater(() -> {
                String text = enterArea.getText();
                enterArea.clear();
                messageService.sendMessage(text, selectedUser);
            });
        }
        //new Thread(() -> messageService.sendMessage("message", selectedUser)).start();
    }

    @FXML
    public void initialize() {
        namesList = new ListView<>();
        configureListView();
        reloadNames();
    }

    private void reloadNames() {
        namesListData.clear();
        updateNamesList();
        namesList.setItems(namesListData);
    }

    private void updateNamesList() {
        names = messageService.getUsers();
        namesListData.addAll(names);
    }

    private void configureListView() {
        namesList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectedUser = String.valueOf(newValue));
        namesList.setEditable(false);
        namesListPane.getChildren().add(namesList);
        namesList.prefWidthProperty().bind(namesListPane.widthProperty());
        namesList.prefHeightProperty().bind(namesListPane.heightProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initialize();
    }
}
