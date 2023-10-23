package yandex.practicum.utils;

import yandex.practicum.service.HistoryManager;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Status;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static yandex.practicum.tasks.TypeOfTask.SUBTASK;
import static yandex.practicum.tasks.TypeOfTask.TASK;

public class CSVTaskUtils {

    public String toString(Task task) {
        String currentTask = task.getId() + "," + task.getTypeOfTask() + "," + task.getName() + ","
                + task.getStatus() + "," + task.getDescription() + ",";
        if (task.getStartTime() != null) {
            currentTask = currentTask + task.getStartTime().format(Task.FORMATTER) + ",";
        }
        if (task.getEndTime() != null) {
            currentTask = currentTask + task.getEndTime().format(Task.FORMATTER) + ",";
        }
        if (task.getDuration() != null) {
            currentTask = currentTask + task.getDuration().toMinutes() + ",";
        }
        if (task.getTypeOfTask() == SUBTASK) {
            Subtask subtask = (Subtask) task;
            currentTask = currentTask + subtask.getEpicId();
        }
        return currentTask;
    }

    public Task fromString(String value) {
        String[] details = value.split(",");
        if (details[1].equals(TASK.toString())) {
            Task task = new Task(details[2], details[4], details[7], details[5]);
            task.setId(Integer.parseInt(details[0]));
            task.setStatus(taskStatus(details[3]));
            return task;
        } else if (details[1].equals(SUBTASK.toString())) {
            Task task = new Subtask(details[2], details[4], details[7], details[5], Integer.parseInt(details[8]));
            task.setId(Integer.parseInt(details[0]));
            task.setStatus(taskStatus(details[3]));
            return task;
        } else {
            Task task = new Epic(details[2], details[4]);
            task.setId(Integer.parseInt(details[0]));
            task.setStatus(taskStatus(details[3]));
            if (details.length > 5) {
                task.setStartTime(LocalDateTime.parse(details[5], Task.FORMATTER));
                ((Epic) task).setEndTime(LocalDateTime.parse(details[6], Task.FORMATTER));
                task.setDuration(Duration.ofMinutes(Long.parseLong(details[7])));
            }
            return task;
        }
    }

    public String historyToString(HistoryManager manager) {
        List<Task> currentHistory;
        currentHistory = manager.getHistory();
        String currentLine = "";
        for (Task task : currentHistory) {
            currentLine = currentLine + task.getId() + ",";
        }
        currentLine = currentLine.substring(0, currentLine.length() - 1);
        return currentLine;
    }

    public List<Integer> historyFromString(String value) {
        List<Integer> currentList = new ArrayList<>();
        String[] currentValue = value.split(",");
        for (String currentString : currentValue) {
            currentList.add(Integer.parseInt(currentString));
        }
        return currentList;
    }

    private Status taskStatus(String line) {
        return switch (Status.valueOf(line)) {
            case NEW -> Status.NEW;
            case IN_PROGRESS -> Status.IN_PROGRESS;
            case DONE -> Status.DONE;
        };
    }
}
