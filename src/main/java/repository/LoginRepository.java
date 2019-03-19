package repository;

import java.sql.SQLException;

public class LoginRepository extends BaseRepository {


    public int authenticateUser(String username, String password) {
        int    userId = -1;
        String sql    = "SELECT * FROM Users WHERE USERNAME = ? AND pwdcompare(?,PASSWORD,0)=1";
        this.openConnection();
        try {
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("ID");
            }
            return userId;
        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
            e.getErrorCode();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getErrorCode();
                e.getSQLState();
            }
        }
        return 0;
    }
}
