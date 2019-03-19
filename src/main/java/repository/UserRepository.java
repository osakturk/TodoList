package repository;

import api.helper.RequestObject;
import model.UserInfo;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserRepository extends BaseRepository {
    private ResourceBundle options       = ResourceBundle.getBundle("/options");
    private String         defaultLocale = options.getString("DefaultLocale");


    public UserInfo getUserInfo(int userId) {
        UserInfo userInfo = new UserInfo();
        String sql = "SELECT USERNAME AS username," +
                "ID as userId ,FIRSTNAME firstname," +
                "LOCALE AS locale " +
                "FROM Users WHERE ID=? AND STATUS = 1";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userInfo.setUserId(resultSet.getLong("userId"));
                userInfo.setUsername(resultSet.getString("username"));
                userInfo.setFirstname(resultSet.getString("firstname"));
                userInfo.setCurrentLocale(new Locale(resultSet.getString("LOCALE").equals("") ? defaultLocale : resultSet.getString("LOCALE")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
            e.getErrorCode();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.getSQLState();
                e.getErrorCode();
                e.printStackTrace();
            }
        }

        return userInfo;
    }

    public int getUserIdByUserName(String username) {
        int userID = -1;
        try {
            String sql;
            this.openConnection();

            sql = "SELECT ID FROM Users " +
                    "WHERE USERNAME= ? AND STATUS=1";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userID = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.getErrorCode();
                e.getSQLState();
                e.printStackTrace();
            }
        }

        return userID;
    }

    public boolean insertUser(MultivaluedMap<String, String> form, HttpServletRequest request) {
        int id = -1;
        String sql = "insert into Users (FIRSTNAME,USERNAME,PASSWORD,STATUS,LOCALE) VALUES(?,?,pwdencrypt(?),?,?)";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,RequestObject.getString("full_name",form,request));
            preparedStatement.setString(2,RequestObject.getString("user_name",form,request));
            preparedStatement.setString(3,RequestObject.getString("password",form,request));
            preparedStatement.setInt(4,1);
            preparedStatement.setString(5,"tr");
            id = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return id > 0;
    }

    public List<UserInfo> getUsers() {
        List<UserInfo> users = new ArrayList<>();
        String sql = "select * from Users where STATUS = 1";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                UserInfo userInfo = new UserInfo();
                userInfo.setFirstname(resultSet.getString("firstName"));
                userInfo.setUserId(resultSet.getLong("id"));
                userInfo.setUsername(resultSet.getString("userName"));
                userInfo.setCurrentLocale(new Locale(resultSet.getString("locale")));
                users.add(userInfo);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }catch (SQLException e){
                e.getMessage();
            }
        }
        return users;
    }
}
