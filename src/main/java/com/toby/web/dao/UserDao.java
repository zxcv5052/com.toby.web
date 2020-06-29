package com.toby.web.dao;


import com.toby.web.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    /**
     *  생성자를 이용한 DI
     *  public UserDao(DataSource dataSource){
     *         this.dataSource = dataSource;
     *     }
     */

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = dataSource.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "insert  into user(id,name,password) values(?,?,?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }
    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = dataSource.getConnection();


        PreparedStatement ps = conn.prepareStatement(
                "select * from user where id=?"
        );
        ps.setString(1,id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        conn.close();

        return user;
    }
}
