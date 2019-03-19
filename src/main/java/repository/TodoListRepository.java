package repository;

import api.helper.RequestObject;
import com.sun.org.apache.regexp.internal.RE;
import model.TodoList;
import model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoListRepository extends BaseRepository {
    public TodoList getTodoListById(int listId) {
        TodoList todoList = new TodoList();
        String sql = "SELECT tl.NAME as name,tl.ID as id, us.FIRSTNAME as firstName FROM TodoList tl inner join Users us on tl.USERID = us.ID WHERE tl.ID = ? and tl.STATUS = 1";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, listId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setFirstname(resultSet.getString("firstName"));
                todoList.setUserInfo(userInfo);
                todoList.setId(resultSet.getLong("id"));
                todoList.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
            e.getErrorCode();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.getErrorCode();
                e.printStackTrace();
                e.getSQLState();
            }
        }

        return todoList;
    }

    public List<TodoList> getTodoList() {
        List<TodoList> allTodoList = new ArrayList<>();

        String sql = "SELECT tl.ID as id,ISNULL(tl.NAME,'Unknown User') as name, tl.USERID as userId,us.FIRSTNAME FROM TodoList tl inner join Users us on tl.USERID = us.ID WHERE tl.STATUS = 1";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserInfo userInfo = new UserInfo();
                TodoList todoList = new TodoList();
                todoList.setId(resultSet.getInt("id"));
                todoList.setName(resultSet.getString("name"));
                userInfo.setFirstname(resultSet.getString("firstName"));
                userInfo.setUserId(resultSet.getLong("userId"));
                todoList.setUserInfo(userInfo);
                allTodoList.add(todoList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
            e.getErrorCode();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getSQLState();
                e.getErrorCode();
            }
        }
        return allTodoList;
    }

    public boolean createTodoList(MultivaluedMap<String, String> form, HttpServletRequest request) {
        int id = -1;

        String sql = "INSERT INTO TodoList(NAME,USERID,STATUS) VALUES(?,?,1)";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, RequestObject.getString("name", form, request));
            preparedStatement.setInt(2, RequestObject.getInt("user_id",form,request));
            id = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
            e.getErrorCode();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.getSQLState();
                e.getErrorCode();
            }
        }
        return id > 0;
    }
    public String getUserNameById(int userId){
        String userName = "";
        String sql = "select FIRSTNAME as firstName from Users where USERID = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                userName = resultSet.getString("firstName");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return userName;
    }

    public boolean updateTodoList(MultivaluedMap<String, String> form, HttpServletRequest request, int todoListId) {
        int id = -1;

        String sql = "UPDATE TodoList SET NAME = ? WHERE ID = ?";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, RequestObject.getString("name", form, request));
            preparedStatement.setInt(2, todoListId);
            id = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
            e.getErrorCode();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.getErrorCode();
            }
        }
        return id > 0;
    }

    public boolean deleteTodoList(int todoListId) {
        int id = -1;

        String sql = "UPDATE TodoList SET STATUS = 0 WHERE ID = ?";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, todoListId);
            id = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
            e.getErrorCode();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id > 0;
    }
}
