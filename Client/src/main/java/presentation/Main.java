package presentation;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import presentation.ui.config.ClientApplication;

@SpringBootApplication
public class Main {

    public static void main(String... args) {
        Application.launch(ClientApplication.class, args);
    }
}
