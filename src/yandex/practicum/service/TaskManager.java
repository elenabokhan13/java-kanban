package yandex.practicum.service;

import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    void createNewTask(Task task) throws IOException, InterruptedException;

    Map<Integer, Task> getTasks();

    Set<Task> getPrioritizedTasks();

    void deleteAllTasks() throws IOException, InterruptedException;

    Task getTaskById(int curId) throws IOException, InterruptedException;

    void deleteTask(int curId) throws IOException, InterruptedException;

    void updateTask(Task task) throws IOException, InterruptedException;

    void createNewEpic(Epic epic) throws IOException, InterruptedException;

    Map<Integer, Epic> getEpics();

    void deleteAllEpics() throws IOException, InterruptedException;

    Epic getEpicById(int curId) throws IOException, InterruptedException;

    void deleteEpic(int curId) throws IOException, InterruptedException;

    void updateEpic(Epic epic) throws IOException, InterruptedException;

    void createNewSubtask(Subtask subtask) throws IOException, InterruptedException;

    Map<Integer, Subtask> getSubtasks();

    void deleteAllSubtasks() throws IOException, InterruptedException;

    Subtask getSubtaskById(int curId) throws IOException, InterruptedException;

    void deleteSubtask(int curId) throws IOException, InterruptedException;

    void updateSubtask(Subtask subtask) throws IOException, InterruptedException;

    List<Subtask> printSubtasksInEpicList(Epic epic);

    List<Task> getHistory();
}

