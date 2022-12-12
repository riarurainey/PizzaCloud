package rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RSocketReqRespClientApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RSocketReqRespClientApplication.class, args);
        Thread.sleep(1000);
    }
}
