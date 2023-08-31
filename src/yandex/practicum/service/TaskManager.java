package yandex.practicum.service;

import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

public interface TaskManager {

    void createNewTask(Task task);

    void printTaskList();

    void deleteAllTasks();

    void getTaskById(int curId);

    void deleteTask(int curId);

    void updateTask(Task task);

    void createNewEpic(Epic epic);

    void printEpicList();

    void deleteAllEpics();

    void getEpicById(int curId);

    void deleteEpic(int curId);

    void updateEpic(Epic epic);

    void createNewSubtask(Subtask subtask);

    void printSubtaskList();

    void deleteAllSubtasks();

    void getSubtaskById(int curId);

    void deleteSubtask(int curId);

    void updateSubtask(Subtask subtask);

    void printSubtasksInEpicList(Epic epic);
}

