package repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class BaseRepository {
    PreparedStatement preparedStatement;
    ResultSet         resultSet;
    private Context           ctx;
    private DataSource        dataSource;
    Connection        connection;


    BaseRepository(){
        try {
            this.ctx = new InitialContext();
            String resourceName = "java:comp/env/jdbc/todolist";
            this.dataSource = (DataSource) ctx.lookup(resourceName);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    void openConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
            this.connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
