package presentation.ui.controller;

import application.messaging.MessageService;
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
import java.util.AbstractMap.SimpleEntry;
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
    private ObservableList<String> namesListData = FXCollections.observableArrayList();

    MessageService messageService;
    private List<String> names;
    private final ScheduledExecutorService scheduledExecutorService;
    private String userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        namesList = new ListView<>();
        configureListView();
        reloadNames();
    }

    @Autowired
    public MessagingInterfaceController(MessageService messageService) {
        this.messageService = messageService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::updateMessages, 1000, 3000, TimeUnit.MILLISECONDS);
    }

    @FXML
    public void sendMessage() {
        if (selectedUser != null && !enterArea.getText().equals("")) {
            String text = enterArea.getText();
            enterArea.clear();
            appendToMessageBoard(text, this.userName);
            new Thread(() -> messageService.sendMessage(text, selectedUser)).start();
        }
    }

    private void updateMessages() {
        names.stream()
                .map(name -> new SimpleEntry<>(name, messageService.getMessage(name)))
                .filter(entry -> !entry.getValue().equals(""))
                .forEach(this::addMessagesToLocalCache);
    }

    private void appendToMessageBoard(String selectedUsersMessage, String selectedUser) {
        messageBoard.appendText("\n" + selectedUser + ": " + selectedUsersMessage);
    }

    private void addMessagesToLocalCache(SimpleEntry<String, String> entry) {
        appendToMessageBoard(entry.getValue(), entry.getKey());
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
        namesList.getSelectionModel().selectFirst();
        namesList.prefWidthProperty().bind(namesListPane.widthProperty());
        namesList.prefHeightProperty().bind(namesListPane.heightProperty());
    }

    public void setUserName(String name) {
        this.userName = name;
    }
}
