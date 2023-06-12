package com.gagana.hospital.api.config;

import com.gagana.hospital.api.config.properties.R2dbcDatabaseProperties;
import io.r2dbc.pool.PoolingConnectionFactoryProvider;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * Refer any extra methods by extends AbstractR2dbcConfiguration
 */
@Order(value = 2)
@Configuration
@Import(value = {R2dbcAutoConfiguration.class})
@EnableR2dbcRepositories
@EnableTransactionManagement
@EnableR2dbcAuditing
@Slf4j
public class DatabaseConfig extends AbstractR2dbcConfiguration {


    private final TextEncryptor textEncryptor;

    private final R2dbcDatabaseProperties r2dbcDatabaseProperties;

    public DatabaseConfig(TextEncryptor textEncryptor, R2dbcDatabaseProperties r2dbcDatabaseProperties) {
        this.textEncryptor = textEncryptor;
        this.r2dbcDatabaseProperties = r2dbcDatabaseProperties;
    }

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions.Builder builder = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, r2dbcDatabaseProperties.getDriver())
                .option(ConnectionFactoryOptions.PROTOCOL, r2dbcDatabaseProperties.getProtocol())
                .option(ConnectionFactoryOptions.HOST, r2dbcDatabaseProperties.getHost())
                .option(ConnectionFactoryOptions.PORT, r2dbcDatabaseProperties.getPort())
                .option(ConnectionFactoryOptions.USER, r2dbcDatabaseProperties.getUser())
                //.option(ConnectionFactoryOptions.PASSWORD, textEncryptor.decrypt(String.valueOf(r2dbcDatabaseProperties.getPassword())))
                //.option(ConnectionFactoryOptions.PASSWORD,"changeme")
                .option(ConnectionFactoryOptions.DATABASE, r2dbcDatabaseProperties.getDatabase())
                ;

        String pwd = String.valueOf(r2dbcDatabaseProperties.getPassword());
        if(BooleanUtils.isFalse(r2dbcDatabaseProperties.getEncrypted())) {
            builder.option(ConnectionFactoryOptions.PASSWORD,pwd);
            log.debug("Use encrypted value of db password {}", textEncryptor.encrypt(pwd));
        } else {
            builder.option(ConnectionFactoryOptions.PASSWORD,textEncryptor.decrypt(pwd));
        }
        if(Objects.nonNull(r2dbcDatabaseProperties.getPooling())) {
            builder.option(PoolingConnectionFactoryProvider.INITIAL_SIZE, r2dbcDatabaseProperties.getPooling().getInitialSize())
                    .option(PoolingConnectionFactoryProvider.MAX_SIZE, r2dbcDatabaseProperties.getPooling().getMaxSize())
                    .option(PoolingConnectionFactoryProvider.MAX_IDLE_TIME, r2dbcDatabaseProperties.getPooling().getMaxIdleTimeInMinutes());
        }
        if(!CollectionUtils.isEmpty(r2dbcDatabaseProperties.getOptions())) {
            r2dbcDatabaseProperties.getOptions().entrySet().forEach((entry) -> {
                builder.option(Option.valueOf(entry.getKey()), entry.getValue());
            });
        }
        return ConnectionFactories.get(builder.build());
    }


    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

}
