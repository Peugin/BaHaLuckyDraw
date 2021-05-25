package tw.peugin.baha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "tw.peugin.heroku.db")
public class BahaLuckyDrawApplication {

    public static void main(String[] args) {
        SpringApplication.run(BahaLuckyDrawApplication.class, args);
    }

}
