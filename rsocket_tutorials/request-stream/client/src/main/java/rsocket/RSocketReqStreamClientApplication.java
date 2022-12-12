package rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RSocketReqStreamClientApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RSocketReqStreamClientApplication.class, args);
        Thread.sleep(10000);
    }
}
