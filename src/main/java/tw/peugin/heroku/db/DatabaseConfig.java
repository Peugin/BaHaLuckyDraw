package tw.peugin.heroku.db;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${SPRING_DATASOURCE_URL}")
    private String dbUrl;
    @Value("${SPRING_DATASOURCE_USERNAME}")
    private String userName;
    @Value("${SPRING_DATASOURCE_PASSWORD}")
    private String password;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(userName);
        config.setPassword(password);
        return new HikariDataSource(config);
    }
}