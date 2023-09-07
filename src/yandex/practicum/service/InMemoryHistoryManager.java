package yandex.practicum.service;

import yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> getHistory = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public void addTask(Task task) {
        if (task != null) {
            if (getHistory.size() == MAX_HISTORY_SIZE) {
                getHistory.remove(0);
            }
            getHistory.add(task);
        } else {
            System.out.println("Данная задача еще не создана.");
        }
    }

    @Override
    public List<Task> getHistory() {
        return getHistory;
    }
}
