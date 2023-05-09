package com.jfcbxp.supervendedor.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.Objects;

import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;
import static io.r2dbc.spi.ConnectionFactoryOptions.SSL;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

@EnableR2dbcRepositories
@Configuration
@RequiredArgsConstructor
public class R2dbcConfig extends AbstractR2dbcConfiguration {
    private final Environment env;
    @Bean
    @Override
    public ConnectionFactory connectionFactory() {

        final ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, Objects.requireNonNull(env.getProperty("sql.database.driver")))
                .option(HOST, Objects.requireNonNull(env.getProperty("sql.database.host")))
                .option(PORT, Integer.parseInt(Objects.requireNonNull(env.getProperty("sql.database.port"))))
                .option(USER, Objects.requireNonNull(env.getProperty("sql.database.username")))
                .option(PASSWORD, Objects.requireNonNull(env.getProperty("sql.database.password")))
                .option(DATABASE, Objects.requireNonNull(env.getProperty("sql.database.database")))
                .option(SSL, false)
                .build();


        return ConnectionFactories.get(options);
    }



    @Bean
    public DatabaseClient r2dbcDatabaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.create(connectionFactory);
    }
}
