package presentation;

import presentation.port.BulletinBoardInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    public static void main(String... args) {
        BulletinBoardInterface service = SpringApplication
                .run(ClientApplication.class, args).getBean(BulletinBoardInterface.class);

        service.postMessage("{\n" +
                "  \"cell\": 3,\n" +
                "  \"tag\": \"2a1073a6e67f0e5f09a5957c659503c690efe7272be8313df872556a9a684d8c\",\n" +
                "  \"message\": \"value\"\n" +
                "}");

        String response = service.getMessage("{\n" +
                "  \"cell\": 3,\n" +
                "  \"tag\": \"tag\"\n" +
                "}");

        System.out.println(response);
    }
}
