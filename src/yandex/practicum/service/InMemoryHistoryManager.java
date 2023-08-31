package yandex.practicum.service;

import yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> getHistory = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        if (getHistory.size() == 10) {
            getHistory.remove(0);
        }
        getHistory.add(task);
    }

    @Override
    public void getHistory() {
        System.out.println(getHistory);
    }
}
