package tool.service;

import tool.tasks.Epic;
import tool.tasks.Subtask;
import tool.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

import static tool.tasks.Status.*;

public class Manager {
    private int currentId = 1;
    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();

    public void createNewTask(Task task) {
        task.setId(currentId);
        taskList.put(task.getId(), task);
        System.out.println("Задача создана.");
        currentId++;
    }

    public void printTaskList() {
        for (Task task : taskList.values()) {
            System.out.println(task);
            System.out.println();
        }
    }

    public void deleteAllTasks() {
        taskList.clear();
        System.out.println("Все задачи успешно удалены.");
    }

    public void getTaskById(int curId) {
        System.out.println(taskList.get(curId));
    }

    public void deleteTask(int curId) {
        taskList.remove(curId);
        System.out.println("Задача успешно удалена.");
    }

    public void renewTask(Task task) {
        taskList.put(task.getId(), task);
    }

    public void createNewEpic(Epic epic) {
        epic.setId(currentId);
        epicList.put(epic.getId(), epic);
        System.out.println("Эпик создан.");
        currentId++;
    }

    public void printEpicList() {
        for (Epic epic : epicList.values()) {
            System.out.println(epic);
            System.out.println();
        }
    }

    public void deleteAllEpics() {
        epicList.clear();
        subtaskList.clear();
        System.out.println("Все эпики успешно удалены.");
    }

    public void getEpicById(int curId) {
        System.out.println(epicList.get(curId));
    }

    public void deleteEpic(int curId) {
        for (Integer curEpicSubtaskId : epicList.get(curId).getSubtasksList()) {
            subtaskList.remove(curEpicSubtaskId);
        }
        epicList.remove(curId);
        System.out.println("Эпик и его подзадачи успешно удалены.");
    }

    public void renewEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
    }

    public void createNewSubtask(Subtask subtask) {
        subtask.setId(currentId);
        subtaskList.put(subtask.getId(), subtask);
        Epic curEpic = epicList.get(subtask.getEpicId());
        curEpic.getSubtasksList().add(subtask.getId());
        System.out.println("Подзадача создана.");
        currentId++;
    }

    public void printSubtaskList() {
        for (Subtask subtask : subtaskList.values()) {
            System.out.println(subtask);
            System.out.println();
        }
    }

    public void deleteAllSubtasks() {
        subtaskList.clear();
        System.out.println("Все подзадачи успешно удалены.");
    }

    public void getSubtaskById(int curId) {
        System.out.println(subtaskList.get(curId));
    }

    public void deleteSubtask(int curId) {
        subtaskList.remove(curId);
        System.out.println("Подзадача успешно удалена.");
    }

    public void renewSubtask(Subtask subtask) {
        subtaskList.put(subtask.getId(), subtask);
        Epic curEpic = epicList.get(subtask.getEpicId());
        setEpicStatus(curEpic);
    }

    public void printSubtasksInEpicList(Epic epic) {
        for (Integer curId : epic.getSubtasksList()) {
            Subtask subtask = subtaskList.get(curId);
            System.out.println(subtask);
        }
    }

    private void setEpicStatus(Epic epic) {
        String currentStatus = "";
        for (Integer subtaskId : epic.getSubtasksList()) {
            Subtask subtask = subtaskList.get(subtaskId);
            if (subtask.getStatus() == NEW) {
                if (currentStatus.equals("") || currentStatus.equals("NEW")) {
                    currentStatus = "NEW";
                } else {
                    epic.setStatus(IN_PROGRESS);
                    break;
                }
            } else if (subtask.getStatus() == IN_PROGRESS) {
                epic.setStatus(IN_PROGRESS);
                break;
            } else if (subtask.getStatus() == DONE) {
                if (currentStatus.equals("") || currentStatus.equals("DONE")) {
                    currentStatus = "DONE";
                } else {
                    epic.setStatus(IN_PROGRESS);
                    break;
                }
            }
        }
        if (currentStatus.equals("DONE")) {
            epic.setStatus(DONE);
        }
    }
}

