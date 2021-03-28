package com.zhike.core.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author 一一哥Sun
 * @Date Created in 2020/3/30
 * @Description 第二种配置数据源的方式
 */
@Data
@ComponentScan
@Configuration
@ConfigurationProperties(prefix="spring.datasource")
public class DbConfig {

    private String url;
    private String username;
    private String password;

    @Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}