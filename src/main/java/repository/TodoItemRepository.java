package repository;

import api.helper.RequestObject;
import model.TodoItem;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoItemRepository extends BaseRepository {

    public List<TodoItem> getTodoItemByTodoListId(MultivaluedMap<String, String> form, HttpServletRequest request, int id) {
        int nameIndex = 0;
        int completeIndex = 0;
        int deadLineIndex = 0;
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "select NAME as name,ID as id ,DESCRIPTION as description, DEADLINE as deadLine,COMPLETE as complete from TodoItems where TODOLISTID = ? and STATUS = 1";
        if (!RequestObject.getString("search_name", form, request).equals("") ){
            if (!RequestObject.getString("search_name", form, request).equals("-1")) {
                sql += " and NAME like ?";
                nameIndex = 2;
            }
        }
        if (!RequestObject.getString("search_complete", form, request).equals("")){
            if (RequestObject.getInt("search_complete", form, request) != -1){
                sql += " and COMPLETE like ?";
                if (nameIndex == 2){
                    completeIndex = 3;
                }else{
                    completeIndex = 2;
                }
            }
        }
        if (!RequestObject.getString("search_dead_line", form, request).equals("") ){
            if (!RequestObject.getString("search_dead_line", form, request).equals("-1")) {
                sql += " and DEADLINE like ?";
                if (completeIndex == 3){
                    deadLineIndex = 4;
                }else if(completeIndex == 2){
                    deadLineIndex = 3;
                }else {
                    deadLineIndex = 2;
                }
            }
        }
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            if (!RequestObject.getString("search_name", form, request).equals("")){
                if (!RequestObject.getString("search_name", form, request).equals("-1")){
                    preparedStatement.setString(nameIndex,"%"+RequestObject.getString("search_name", form, request)+"%");
                    System.out.println(nameIndex);
                }
            }
            if (!RequestObject.getString("search_complete", form, request).equals("")){
                if (RequestObject.getInt("search_complete", form, request) != -1){
                    preparedStatement.setInt(completeIndex,RequestObject.getInt("search_complete", form, request));
                    System.out.println(completeIndex);
                }
            }
            if (!RequestObject.getString("search_dead_line", form, request).equals("")){
                if (!RequestObject.getString("search_dead_line", form, request).equals("-1")){
                    preparedStatement.setString(deadLineIndex,"%"+RequestObject.getString("search_dead_line", form, request)+"%");
                    System.out.println(deadLineIndex);
                }
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TodoItem todoItem = new TodoItem();
                todoItem.setName(resultSet.getString("name"));
                todoItem.setDescription(resultSet.getString("description"));
                todoItem.setDeadLine(resultSet.getString("deadLine"));
                todoItem.setComplete(resultSet.getByte("complete"));
                todoItem.setId(resultSet.getLong("id"));
                todoItems.add(todoItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoItems;
    }

    public boolean createTodoItem(MultivaluedMap<String, String> form, HttpServletRequest request) {
        int id = -1;

        String sql = "INSERT INTO TodoItems(NAME,DESCRIPTION,DEADLINE,COMPLETE,STATUS,TODOLISTID) VALUES(?,?,?,?,?,1)";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, RequestObject.getString("name", form, request));
            preparedStatement.setString(2, RequestObject.getString("description", form, request));
            preparedStatement.setString(3, RequestObject.getString("dead_line", form, request));
            preparedStatement.setInt(4, RequestObject.getInt("complete_status", form, request));
            preparedStatement.setInt(5, RequestObject.getInt("list_id", form, request));
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
            }
        }
        return id > 0;
    }

    public boolean deleteTodoItem(int itemId) {
        int id = -1;
        String sql = "UPDATE TodoItems SET STATUS = 0 WHERE ID = ?";
        this.openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, itemId);
            id = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.getErrorCode();
            }
        }
        return id > 0;
    }
}
