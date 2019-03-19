package api.provider;

import model.TodoList;
import org.json.JSONArray;
import org.json.JSONObject;
import repository.TodoListRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public class ListDefinitions {

    public static JSONObject getTodoList() {
        JSONObject result = new JSONObject();
        JSONArray todoListArray = new JSONArray();
        TodoListRepository repository = new TodoListRepository();

        List<TodoList> todoList = repository.getTodoList();
        for (TodoList todo: todoList) {
            JSONObject todoObject = new JSONObject();
            todoObject.put("id",todo.getId());
            todoObject.put("name",todo.getName());
            todoObject.put("user_id",todo.getUserInfo().getUserId());
            todoObject.put("user_name",todo.getUserInfo().getFirstname());
            todoListArray.put(todoObject);
        }
        result.put("data",todoListArray);
        return result;
    }

    public static JSONObject getTodoListById(int id) {
        TodoListRepository repository = new TodoListRepository();
        TodoList           todoList   = repository.getTodoListById(id);
        JSONObject todoObject = new JSONObject();
        todoObject.put("id",todoList.getId());
        todoObject.put("name",todoList.getName());
        todoObject.put("user_first_name", todoList.getUserInfo().getFirstname());
        return todoObject;
    }

    public static boolean createTodoList(HttpServletRequest request, MultivaluedMap<String, String> form) {
        TodoListRepository repository = new TodoListRepository();
        return repository.createTodoList(form, request);
    }

    public static boolean updateTodoList(HttpServletRequest request, MultivaluedMap<String, String> form, int id) {
        TodoListRepository repository = new TodoListRepository();
        return repository.updateTodoList(form, request, id);
    }

    public static boolean deleteTodoList(int id) {
        TodoListRepository repository = new TodoListRepository();
        return repository.deleteTodoList(id);
    }
}
