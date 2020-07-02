package com.toby.web.dao;

import com.sun.rowset.internal.Row;
import com.toby.web.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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

        /** 익명 내부 클래스 생성
         * this.jdbcContext.workWithStatement(
         *             new StatementStrategy() {
         *                 @Override
         *                 public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
         *                     PreparedStatement ps =  c.prepareStatement(
         *                                                      "insert  into user(id,name,password) values(?,?,?)"
         *                                              );
         *                     ps.setString(1, user.getId());
         *                     ps.setString(2,user.getName());
         *                     ps.setString(3,user.getPassword());
         *
         *                     return ps;
         *                 }
         *             }
         *         );
         */
        System.out.println(user.getId()+ "," + user.getName() +"," + user.getPassword());
        this.jdbcTemplate.update("insert into user(id,name,password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
    }
    public User get(String id) throws ClassNotFoundException, SQLException {
       return this.jdbcTemplate.queryForObject("select * from user where id=?",
               new Object[]{id},this.userMapper);
    }

    public void deleteAll() throws SQLException{
        this.jdbcTemplate.update("delete from user");
    }

    public int getCount() throws SQLException{
        return this.jdbcTemplate.queryForObject("select count(*) from user", int.class);
    }

    public List<User> getAll(){
        return this.jdbcTemplate.query("select * from user order by id",
                this.userMapper);
    }

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
            };
}
