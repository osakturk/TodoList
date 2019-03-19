package model;

public class TodoItem {
    private Long id;
    private String name;
    private String description;
    private String createDate;
    private long todoListId;
    private String deadLine;
    private short status;
    private short complete;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(long todoListId) {
        this.todoListId = todoListId;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getComplete() {
        return complete;
    }

    public void setComplete(short complete) {
        this.complete = complete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
