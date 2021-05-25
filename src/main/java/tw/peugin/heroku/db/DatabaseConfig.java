package tw.peugin.heroku.db;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
        return DataSourceBuilder
                .create()
                .driverClassName(driveClassName)
                .url(dbUrl)
                .username(userName)
                .password(password)
                .build();
    }
}