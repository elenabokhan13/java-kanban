package yandex.practicum.service;

import yandex.practicum.exceptions.ManagerSaveException;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    void createNewTask(Task task) throws IOException, ManagerSaveException;

    Map<Integer, Task> getTasks();

    void deleteAllTasks() throws IOException, ManagerSaveException;

    Task getTaskById(int curId) throws IOException, ManagerSaveException;

    void deleteTask(int curId) throws IOException, ManagerSaveException;

    void updateTask(Task task) throws IOException, ManagerSaveException;

    void createNewEpic(Epic epic) throws IOException, ManagerSaveException;

    Map<Integer, Epic> getEpics();

    void deleteAllEpics() throws IOException, ManagerSaveException;

    Epic getEpicById(int curId) throws IOException, ManagerSaveException;

    void deleteEpic(int curId) throws IOException, ManagerSaveException;

    void updateEpic(Epic epic) throws IOException, ManagerSaveException;

    void createNewSubtask(Subtask subtask) throws IOException, ManagerSaveException;

    Map<Integer, Subtask> getSubtasks();

    void deleteAllSubtasks() throws IOException, ManagerSaveException;

    Subtask getSubtaskById(int curId) throws IOException, ManagerSaveException;

    void deleteSubtask(int curId) throws IOException, ManagerSaveException;

    void updateSubtask(Subtask subtask) throws IOException, ManagerSaveException;

    void printSubtasksInEpicList(Epic epic);

    List<Task> getHistory();
}

