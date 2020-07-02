package com.toby.web.dao;


import com.toby.web.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private DataSource dataSource;
    private JdbcContext jdbcContext;

    public void setDataSource(DataSource dataSource)
    {
        this.jdbcContext = new JdbcContext();

        this.jdbcContext.setDataSource(dataSource);

        this.dataSource = dataSource;
    }

    /**
     *  생성자를 이용한 DI
     *  public UserDao(DataSource dataSource){
     *         this.dataSource = dataSource;
     *     }
     */

    public void add(final User user) throws ClassNotFoundException, SQLException {
        /** 로컬 클래스 사용할 때
         *  class AddStatement implements StatementStrategy{
         *             User user;
         *             public AddStatement(User user){
         *                 this.user = user;
         *             }
         *             public PreparedStatement makePreparedStatement(Connection c) throws SQLException{
         *                 PreparedStatement ps = c.prepareStatement(
         *                         "insert  into user(id,name,password) values(?,?,?)"
         *                 );
         *                 ps.setString(1, user.getId());
         *                 ps.setString(2,user.getName());
         *                 ps.setString(3,user.getPassword());
         *
         *                 return ps;
         *             }
         *         }
         */
        // 익명 내부 클래스 생성
        this.jdbcContext.workWithStatement(
            new StatementStrategy() {
                @Override
                public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                    PreparedStatement ps =  c.prepareStatement(
                                                     "insert  into user(id,name,password) values(?,?,?)"
                                             );
                    ps.setString(1, user.getId());
                    ps.setString(2,user.getName());
                    ps.setString(3,user.getPassword());

                    return ps;
                }
            }
        );
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

    public void deleteAll() throws SQLException{
        this.jdbcContext.workWithStatement(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        return c.prepareStatement("delete from user");
                    }
                }
        );
    }

    public int getCount() throws SQLException{
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from user");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

}
