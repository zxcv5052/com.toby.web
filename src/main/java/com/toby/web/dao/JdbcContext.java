package com.toby.web.dao;

import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

@Configuration
public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void workWithStatement(StatementStrategy stmt) throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = this.dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        }catch (SQLException e){
            throw e;
        }finally {
            if(ps!=null) try{ ps.close(); } catch (SQLException e){}
            if(c!=null) try{ c.close(); } catch (SQLException e){}
        }
    }
    // String... parameter  -> 가변 인자
    public void executeSQL(final String query, final String... parameter) throws SQLException{
        workWithStatement(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        PreparedStatement ps = c.prepareStatement(query);
                        int index = 1;
                        for(String o : parameter){
                            ps.setString(index++,o);
                        }
                        return ps;
                    }
                }
        );
    }
}
