package model;

import java.util.List;

public class TodoList {
    private long id;
    private String name;
    private List<TodoItem> todoItemList;
    private UserInfo userInfo;
    private short status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TodoItem> getTodoItemList() {
        return todoItemList;
    }

    public void setTodoItemList(List<TodoItem> todoItemList) {
        this.todoItemList = todoItemList;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
