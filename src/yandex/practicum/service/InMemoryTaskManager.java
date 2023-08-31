package yandex.practicum.service;

import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;
import yandex.practicum.tasks.Status;

import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int currentId = 1;
    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();

    @Override
    public void createNewTask(Task task) {
        task.setId(currentId);
        taskList.put(task.getId(), task);
        System.out.println("Задача создана.");
        currentId++;
    }

    @Override
    public void printTaskList() {
        for (Task task : taskList.values()) {
            System.out.println(task);
            System.out.println();
        }
    }

    @Override
    public void deleteAllTasks() {
        taskList.clear();
        System.out.println("Все задачи успешно удалены.");
    }

    @Override
    public void getTaskById(int curId) {
        System.out.println(taskList.get(curId));
        Managers.getDefaultHistory().addTask(taskList.get(curId));
    }

    @Override
    public void deleteTask(int curId) {
        taskList.remove(curId);
        System.out.println("Задача успешно удалена.");
    }

    @Override
    public void updateTask(Task task) {
        taskList.put(task.getId(), task);
    }

    @Override
    public void createNewEpic(Epic epic) {
        epic.setId(currentId);
        epicList.put(epic.getId(), epic);
        System.out.println("Эпик создан.");
        currentId++;
    }

    @Override
    public void printEpicList() {
        for (Epic epic : epicList.values()) {
            System.out.println(epic);
            System.out.println();
        }
    }

    @Override
    public void deleteAllEpics() {
        subtaskList.clear();
        epicList.clear();
        System.out.println("Все эпики успешно удалены.");
    }

    @Override
    public void getEpicById(int curId) {
        System.out.println(epicList.get(curId));
        Managers.getDefaultHistory().addTask(epicList.get(curId));
    }

    @Override
    public void deleteEpic(int curId) {
        for (Integer curEpicSubtaskId : epicList.get(curId).getSubtaskIds()) {
            subtaskList.remove(curEpicSubtaskId);
        }
        epicList.remove(curId);
        System.out.println("Эпик и его подзадачи успешно удалены.");
    }

    @Override
    public void updateEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        subtask.setId(currentId);
        subtaskList.put(subtask.getId(), subtask);
        Epic curEpic = epicList.get(subtask.getEpicId());
        curEpic.getSubtaskIds().add(subtask.getId());
        setEpicStatus(curEpic);
        System.out.println("Подзадача создана.");
        currentId++;
    }

    @Override
    public void printSubtaskList() {
        for (Subtask subtask : subtaskList.values()) {
            System.out.println(subtask);
            System.out.println();
        }
    }

    @Override
    public void deleteAllSubtasks() {
        subtaskList.clear();
        System.out.println("Все подзадачи успешно удалены.");
    }

    @Override
    public void getSubtaskById(int curId) {
        System.out.println(subtaskList.get(curId));
        Managers.getDefaultHistory().addTask(subtaskList.get(curId));
    }

    @Override
    public void deleteSubtask(int curId) {
        subtaskList.remove(curId);
        System.out.println("Подзадача успешно удалена.");
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtaskList.put(subtask.getId(), subtask);
        Epic curEpic = epicList.get(subtask.getEpicId());
        setEpicStatus(curEpic);
    }

    @Override
    public void printSubtasksInEpicList(Epic epic) {
        for (Integer curId : epic.getSubtaskIds()) {
            Subtask subtask = subtaskList.get(curId);
            System.out.println(subtask);
        }
    }

    private void setEpicStatus(Epic epic) {
        for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
            int curSubtaskId = epic.getSubtaskIds().get(i);
            Subtask subtask = subtaskList.get(curSubtaskId);
            if (i == 0) {
                if (subtask.getStatus() == Status.DONE) {
                    epic.setStatus(Status.DONE);
                } else if (subtask.getStatus() == Status.NEW) {
                    epic.setStatus(Status.NEW);
                } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                    epic.setStatus(Status.IN_PROGRESS);
                    break;
                }
            } else {
                if (subtask.getStatus() == Status.NEW) {
                    if (epic.getStatus() == Status.DONE) {
                        epic.setStatus(Status.IN_PROGRESS);
                        break;
                    }
                } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                    epic.setStatus(Status.IN_PROGRESS);
                    break;
                } else if (subtask.getStatus() == Status.DONE) {
                    if (epic.getStatus() == Status.NEW) {
                        epic.setStatus(Status.IN_PROGRESS);
                        break;
                    }
                }
            }
        }
    }
}

