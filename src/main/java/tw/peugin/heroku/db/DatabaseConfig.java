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

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(userName);
        config.setPassword(password);
        return new HikariDataSource(config);
    }
}