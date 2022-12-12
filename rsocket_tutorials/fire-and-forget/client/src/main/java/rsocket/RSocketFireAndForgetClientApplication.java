package rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RSocketFireAndForgetClientApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RSocketFireAndForgetClientApplication.class, args);
        Thread.sleep(1000);
    }
}
