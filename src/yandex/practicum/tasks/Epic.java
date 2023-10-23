package yandex.practicum.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();
    LocalDateTime endTime;


    public Epic(String name, String description) {
        super(name, description);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public TypeOfTask getTypeOfTask() {
        return TypeOfTask.EPIC;
    }

    @Override
    public String toString() {
        String string_new = "Тип: эпик\nНомер эпика: " + id
                + "\n" + "Название эпика: " + name + "\n"
                + "Описание эпика: " + description + "\n" + "Статус: " + status + "\n";
        if (startTime != null) {
            string_new = string_new + "Время начала эпика: " + getStartTime().format(Task.FORMATTER) + ",\n";
        }
        if (endTime != null) {
            string_new = string_new + "Время завершения эпика: " + getEndTime().format(Task.FORMATTER) + ",\n";
        }
        if (duration != null) {
            string_new = string_new + "Длительность выполнения: " + getDuration().toMinutes() + " минут\n";
        }
        return string_new;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Epic otherEpic = (Epic) obj;
        return Objects.equals(name, otherEpic.name) &&
                Objects.equals(description, otherEpic.description);
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
