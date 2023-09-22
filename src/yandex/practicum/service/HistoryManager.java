package yandex.practicum.service;

import yandex.practicum.tasks.Task;

import java.util.List;

public interface HistoryManager {

    void addTask(Task task);

    void remove(int id);

    List<Task> getHistory();
}
