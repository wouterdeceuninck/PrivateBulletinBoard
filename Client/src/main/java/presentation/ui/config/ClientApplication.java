package presentation.ui.config;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import presentation.Main;

public class ClientApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(Main.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public class StageReadyEvent extends ApplicationEvent {
        private int amount;

        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public StageReadyEvent(Stage stage, int amount) {
            super(stage);
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
