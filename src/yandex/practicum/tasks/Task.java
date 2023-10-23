package yandex.practicum.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH:mm");

    public Task(String name, String description, String duration, String startTime) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.duration = Duration.ofMinutes(Long.parseLong(duration));
        this.startTime = LocalDateTime.parse(startTime, FORMATTER);
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TypeOfTask getTypeOfTask() {
        return TypeOfTask.TASK;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    @Override
    public String toString() {
        return "Тип: простая задача\nНомер задачи: " + id + "\n"
                + "Название задачи: " + name + "\n"
                + "Описание задачи: " + description + "\n" + "Статус: " + status + "\n"
                + "Время начала задачи: " + startTime.format(FORMATTER) + "\n"
                + "Время завершения задачи: " + getEndTime().format(FORMATTER) + "\n"
                + "Длительность выполнения: " + duration.toMinutes() + " минут\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(name, otherTask.name) &&
                Objects.equals(description, otherTask.description);
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
