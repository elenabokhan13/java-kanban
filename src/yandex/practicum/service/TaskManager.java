package yandex.practicum.service;

import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    void createNewTask(Task task);

    Map<Integer, Task> getTasks();

    Set<Task> getPrioritizedTasks();

    void deleteAllTasks();

    Task getTaskById(int curId);

    void deleteTask(int curId);

    void updateTask(Task task);

    void createNewEpic(Epic epic);

    Map<Integer, Epic> getEpics();

    void deleteAllEpics();

    Epic getEpicById(int curId);

    void deleteEpic(int curId);

    void updateEpic(Epic epic);

    void createNewSubtask(Subtask subtask);

    Map<Integer, Subtask> getSubtasks();

    void deleteAllSubtasks();

    Subtask getSubtaskById(int curId);

    void deleteSubtask(int curId);

    void updateSubtask(Subtask subtask);

    void printSubtasksInEpicList(Epic epic);

    List<Task> getHistory();
}

