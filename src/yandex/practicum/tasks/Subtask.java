package yandex.practicum.tasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, String duration, String startTime, int epicId) {
        super(name, description, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TypeOfTask getTypeOfTask() {
        return TypeOfTask.SUBTASK;
    }

    @Override
    public String toString() {
        return "Тип: подзадача\nНомер подзадачи: " + id + "\n"
                + "Название подзадачи: " + name + "\n"
                + "Описание подзадачи: " + description + "\n" + "Статус: " + status + "\n"
                + "Время начала подзадачи: " + startTime.format(FORMATTER) + "\n"
                + "Время завершения подзадачи: " + getEndTime().format(FORMATTER) + "\n"
                + "Длительность выполнения: " + duration.toMinutes() + " минут\n";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (name != null) {
            hash = hash + name.hashCode();
        }
        hash = hash * 31;
        if (description != null) {
            hash = hash + description.hashCode();
        }
        return hash;
    }
}
