package presentation;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String... args) {
        PropertyConfigurator.configure("/Users/wdeceuninck/IdeaProjects/PrivateBulletinBoard/BulletinBoard/src/main/resources/log4j.properties");
        SpringApplication.run(Main.class, args);
    }
}
