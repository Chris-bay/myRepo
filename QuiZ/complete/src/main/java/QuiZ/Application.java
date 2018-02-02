package QuiZ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public long myLong = 1499;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}
