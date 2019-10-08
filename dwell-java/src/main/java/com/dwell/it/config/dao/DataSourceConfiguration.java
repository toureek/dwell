package com.dwell.it.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
@MapperScan("com.dwell.it.dao")
public class DataSourceConfiguration {

    private static final int TIME_OUT_NANO_SECONDS = 10000;
    private static final int ACQUIRE_RETRY_TIMES = 2;

    @Value("${spring.datasource.driver}")
    private String jdbcDriver;

    @Value("${spring.datasource.url}")
    private String jdbcURL;

    @Value("${spring.datasource.username}")
    private String dbUserName;

    @Value("${spring.datasource.password}")
    private String dbPassword;


    @Bean(name="dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcURL);
        dataSource.setUser(dbUserName);
        dataSource.setPassword(dbPassword);
        dataSource.setAutoCommitOnClose(false);
        dataSource.setCheckoutTimeout(TIME_OUT_NANO_SECONDS);
        dataSource.setAcquireRetryAttempts(ACQUIRE_RETRY_TIMES);
        return dataSource;
    }
}

