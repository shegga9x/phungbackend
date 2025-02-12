package com.example.backend.util.ssh;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
    @Value("${db.remote.password}")
    private String dbRemotePassword;
    @Value("${db.remote.user}")
    private String dbRemoteUser;

    @Bean
    @ConditionalOnBean(name = "sshTunnelInitializer")
    @DependsOn("sshTunnelInitializer")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3307/starter-kit-db");
        config.setUsername(dbRemoteUser);
        config.setPassword(dbRemotePassword);
        return new HikariDataSource(config);
    }
}
