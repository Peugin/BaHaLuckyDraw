package tw.peugin.heroku.db;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName}")
    private String driveClassName;

    @Bean
    public DataSource dataSource() {
        System.out.println("pwd:" + password);
        System.out.println("env:" + System.getenv("SPRING_DATASOURCE_PASSWORD"));
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(userName);
        config.setPassword(password);
        config.setDriverClassName(driveClassName);
        return new HikariDataSource(config);
    }
}