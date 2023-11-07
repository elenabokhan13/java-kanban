package yandex.practicum.junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import yandex.practicum.service.*;
import yandex.practicum.tasks.Task;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryManagerTest {
    private HistoryManager historyManager = new InMemoryHistoryManager();
    private TaskManager taskManager = (InMemoryTaskManager) Managers.getDefault();
    private Task task1 = new Task("task1", "Description task1", "35", "19.05.2023_12:00");
    private Task task2 = new Task("task2", "Description task2", "15", "19.05.2023_13:10");
    private Task task3 = new Task("task3", "Description task3", "45", "20.05.2023_18:10");
    private Task task4 = new Task("task4", "Description task4", "15", "29.05.2023_12:10");
    private Task task5 = new Task("task5", "Description task5", "40", "23.05.2023_15:10");

    HistoryManagerTest() throws IOException, InterruptedException {
    }


    @AfterEach
    public void createSetting() throws IOException, InterruptedException {
        taskManager.deleteAllTasks();
    }

    @Test
    public void addTaskTest() throws IOException, InterruptedException {
        taskManager.createNewTask(task1);
        taskManager.createNewTask(task2);
        historyManager.addTask(task1);
        historyManager.addTask(task2);
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history);
        assertEquals(2, history.size());

        historyManager.addTask(task2);
        final List<Task> history1 = historyManager.getHistory();

        assertNotNull(history1);
        assertEquals(2, history1.size());
    }

    @Test
    public void removeTest() throws IOException, InterruptedException {
        taskManager.createNewTask(task1);
        taskManager.createNewTask(task2);
        taskManager.createNewTask(task3);
        taskManager.createNewTask(task4);
        taskManager.createNewTask(task5);

        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.addTask(task4);
        historyManager.addTask(task5);

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history);
        assertEquals(5, history.size());

        historyManager.remove(1);
        historyManager.remove(5);
        historyManager.remove(3);

        final List<Task> history2 = historyManager.getHistory();

        assertNotNull(history2);
        assertEquals(2, history2.size());
    }

    @Test
    public void getHistoryTest() throws IOException, InterruptedException {
        taskManager.createNewTask(task1);
        taskManager.createNewTask(task2);
        taskManager.createNewTask(task3);
        taskManager.createNewTask(task4);
        taskManager.createNewTask(task5);

        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.addTask(task4);
        historyManager.addTask(task5);

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history);
        assertEquals(5, history.size());
    }
}