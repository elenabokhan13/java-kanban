package yandex.practicum.service;

import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;
import yandex.practicum.tasks.Status;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private int currentId = 1;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }


    @Override
    public void createNewTask(Task task) {
        task.setId(currentId);
        tasks.put(task.getId(), task);
        System.out.println("Задача создана.");
        currentId++;
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        System.out.println("Все задачи успешно удалены.");
    }

    @Override
    public Task getTaskById(int curId) {
        inMemoryHistoryManager.addTask(tasks.get(curId));
        return tasks.get(curId);
    }

    @Override
    public void deleteTask(int curId) {
        tasks.remove(curId);
        System.out.println("Задача успешно удалена.");
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void createNewEpic(Epic epic) {
        epic.setId(currentId);
        epics.put(epic.getId(), epic);
        System.out.println("Эпик создан.");
        currentId++;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
        System.out.println("Все эпики успешно удалены.");
    }

    @Override
    public Epic getEpicById(int curId) {
        inMemoryHistoryManager.addTask(epics.get(curId));
        return epics.get(curId);
    }

    @Override
    public void deleteEpic(int curId) {
        for (Integer curEpicSubtaskId : epics.get(curId).getSubtaskIds()) {
            subtasks.remove(curEpicSubtaskId);
        }
        epics.remove(curId);
        System.out.println("Эпик и его подзадачи успешно удалены.");
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        subtask.setId(currentId);
        subtasks.put(subtask.getId(), subtask);
        Epic curEpic = epics.get(subtask.getEpicId());
        curEpic.getSubtaskIds().add(subtask.getId());
        setEpicStatus(curEpic);
        System.out.println("Подзадача создана.");
        currentId++;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public void deleteAllSubtasks() {
        for (Subtask subtask : subtasks.values())
            if (!epics.get(subtask.getEpicId()).getSubtaskIds().isEmpty()) {
                epics.get(subtask.getEpicId()).getSubtaskIds().clear();
                setEpicStatus(epics.get(subtask.getEpicId()));
            }
        subtasks.clear();
        System.out.println("Все подзадачи успешно удалены.");
    }

    @Override
    public Subtask getSubtaskById(int curId) {
        inMemoryHistoryManager.addTask(subtasks.get(curId));
        return subtasks.get(curId);
    }

    @Override
    public void deleteSubtask(int curId) {
        Epic curEpic = epics.get(subtasks.get(curId).getEpicId());
        curEpic.getSubtaskIds().remove((Integer) curId);
        subtasks.remove(curId);
        setEpicStatus(curEpic);
        System.out.println("Подзадача успешно удалена.");
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic curEpic = epics.get(subtask.getEpicId());
        setEpicStatus(curEpic);
    }

    @Override
    public void printSubtasksInEpicList(Epic epic) {
        for (Integer curId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(curId);
            System.out.println(subtask);
        }
    }

    private void setEpicStatus(Epic epic) {
        if (epic.getSubtaskIds().isEmpty()) {
            epic.setStatus(Status.NEW);
        }
        for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
            int curSubtaskId = epic.getSubtaskIds().get(i);
            Subtask subtask = subtasks.get(curSubtaskId);
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

