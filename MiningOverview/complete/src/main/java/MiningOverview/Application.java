package MiningOverview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class Application {

    public long myLong = 1499;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Runnable r = new Runnable() {
            public void run() {
                DataGrabber.run();
            }
        };

        new Thread(r).start();
        //this line will execute immediately, not waiting for your task to complete

    }
}
