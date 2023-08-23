public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected String status;

    public Task(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Тип: простая задача\nНомер задачи:" + id + "\n" + "Название задачи: " + name + "\n"
                + "Описание задачи: " + description + "\n" + "Статус: " + status;
    }
}
