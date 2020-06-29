package com.toby.web.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker{

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // D사의 독자전인 방법으로 Connection을 생성하는 코드.
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/dldldldl", "qwqw","alslsad"
        );
        return conn;
    }
}
