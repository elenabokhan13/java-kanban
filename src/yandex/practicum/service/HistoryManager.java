package yandex.practicum.service;

import yandex.practicum.tasks.Task;

public interface HistoryManager {

    void addTask(Task task);

    void getHistory();
}
