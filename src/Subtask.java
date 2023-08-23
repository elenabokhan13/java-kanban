public class Subtask extends Task {
    public int epicId;

    public Subtask(String name, String description, String status) {
        super(name, description, status);
    }

    @Override
    public String toString() {
        return "Тип: подзадача\nНомер задачи:" + id + "\n" + "Название задачи: " + name + "\n"
                + "Описание задачи: " + description + "\n" + "Статус: " + status;
    }
}
