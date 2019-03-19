package api.provider;

import model.TodoItem;
import org.json.JSONArray;
import org.json.JSONObject;
import repository.TodoItemRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public class ItemDefinitions {
    public static JSONObject getTodoItemByTodoListId(MultivaluedMap<String, String> form, HttpServletRequest request, int id) {
        TodoItemRepository repository = new TodoItemRepository();
        List<TodoItem> todoItem = repository.getTodoItemByTodoListId(form,request,id);
        JSONObject result = new JSONObject();
        JSONArray todoArray = new JSONArray();
        for (TodoItem item : todoItem) {
            JSONObject todoObject = new JSONObject();
            todoObject.put("name", item.getName());
            todoObject.put("description", item.getDescription());
            todoObject.put("dead_line", item.getDeadLine());
            todoObject.put("complete_status", item.getComplete());
            todoObject.put("id", item.getId());
            todoArray.put(todoObject);
        }
        result.put("data", todoArray);
        return result;
    }

    public static boolean createTodoItem(HttpServletRequest request, MultivaluedMap<String, String> form) {
        TodoItemRepository repository = new TodoItemRepository();

        return repository.createTodoItem(form, request);
    }

    public static boolean deleteTodoItem(int id) {
        TodoItemRepository repository = new TodoItemRepository();

        return repository.deleteTodoItem(id);
    }
}
