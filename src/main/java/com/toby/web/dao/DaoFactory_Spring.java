package com.toby.web.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class DaoFactory_Spring {
    @Bean
    public UserDao userDao(){
        // 생성자를 통한 DI
        // return new UserDao(connectionMaker());

        // 수정자 메소드 DI
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/edu_toby_spring");
        dataSource.setUsername("root");
        dataSource.setPassword("cs1234");
        return dataSource;
    }
}
