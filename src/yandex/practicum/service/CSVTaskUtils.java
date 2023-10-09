package yandex.practicum.service;

import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Status;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static yandex.practicum.tasks.TypeOfTask.SUBTASK;
import static yandex.practicum.tasks.TypeOfTask.TASK;

public class CSVTaskUtils {

    String toString(Task task) {
        String currentTask = task.getId() + "," + task.getTypeOfTask() + "," + task.getName() + ","
                + task.getStatus() + "," + task.getDescription() + ",";
        if (task.getTypeOfTask() == SUBTASK) {
            Subtask subtask = (Subtask) task;
            currentTask = currentTask + subtask.getEpicId();
        }
        return currentTask;
    }

    Task fromString(String value) {
        String[] details = value.split(",");
        if (details[1].equals(TASK.toString())) {
            Task task = new Task(details[2], details[4]);
            task.setId(Integer.parseInt(details[0]));
            task.setStatus(taskStatus(details[3]));
            return task;
        } else if (details[1].equals(SUBTASK.toString())) {
            Task task = new Subtask(details[2], details[4], Integer.parseInt(details[5]));
            task.setId(Integer.parseInt(details[0]));
            task.setStatus(taskStatus(details[3]));
            return task;
        } else {
            Task task = new Epic(details[2], details[4]);
            task.setId(Integer.parseInt(details[0]));
            task.setStatus(taskStatus(details[3]));
            return task;
        }
    }

    static String historyToString(HistoryManager manager) {
        List<Task> currentHistory;
        currentHistory = manager.getHistory();
        String currentLine = "";
        for (Task task : currentHistory) {
            currentLine = currentLine + task.getId() + ",";
        }
        currentLine = currentLine.substring(0, currentLine.length() - 1);
        return currentLine;
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> currentList = new ArrayList<>();
        String[] currentValue = value.split(",");
        for (String currentString : currentValue) {
            currentList.add(Integer.parseInt(currentString));
        }
        return currentList;
    }

    private Status taskStatus(String line) {
        switch (Status.valueOf(line)) {
            case NEW -> {
                return Status.NEW;
            }
            case IN_PROGRESS -> {
                return Status.IN_PROGRESS;
            }
            case DONE -> {
                return Status.DONE;
            }
        }
        return null;
    }
}
