package com.juaracoding.pcmspringboot4.config;

import com.juaracoding.pcmspringboot4.security.Crypto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Random;


@Configuration
public class MainConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder datasourceBuilder = DataSourceBuilder.create();
        datasourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        datasourceBuilder.url(Crypto.performDecrypt(env.getProperty("spring.datasource.url")));
        datasourceBuilder.username(Crypto.performDecrypt(env.getProperty("spring.datasource.username")));
        datasourceBuilder.password(Crypto.performDecrypt(env.getProperty("spring.datasource.password")));
        return datasourceBuilder.build();
    }

    @Bean
    public Random getRandom(){
        return new Random();
    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}