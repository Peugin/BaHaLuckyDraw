package tw.peugin.baha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BahaLuckyDrawApplication {
    @Value("${spring.datasource.password}")
    private static String password;
    @Value("${spring.datasource.username}")
    private static String userName;

    public static void main(String[] args) {
        System.out.println("un:" + userName);
        System.out.println("pwd:" + password);
        System.out.println("env:" + System.getenv("SPRING_DATASOURCE_PASSWORD"));
        SpringApplication.run(BahaLuckyDrawApplication.class, args);
        System.out.println("un:" + userName);
        System.out.println("pwd:" + password);
        System.out.println("env:" + System.getenv("SPRING_DATASOURCE_PASSWORD"));
    }

}
