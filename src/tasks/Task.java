package tasks;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Тип: простая задача\nНомер задачи: " + id + "\n" + "Название задачи: " + name + "\n"
                + "Описание задачи: " + description + "\n" + "Статус: " + status;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
