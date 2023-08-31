package yandex.practicum.service;

public class Managers {
    static TaskManager inMemoryTaskManager = new InMemoryTaskManager();
    static HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    public static TaskManager getDefault() {
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return inMemoryHistoryManager;
    }
}
