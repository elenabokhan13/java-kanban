package yandex.practicum.service;

import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;
import yandex.practicum.tasks.Status;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    protected int currentId = 1;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }


    @Override
    public void createNewTask(Task task) throws IOException, ManagerSaveException {
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
    public void deleteAllTasks() throws IOException, ManagerSaveException {
        for (Integer x : tasks.keySet()) {
            inMemoryHistoryManager.remove(x);
        }
        tasks.clear();
        System.out.println("\nВсе задачи успешно удалены.");
    }

    @Override
    public Task getTaskById(int curId) throws IOException, ManagerSaveException {
        inMemoryHistoryManager.addTask(tasks.get(curId));
        return tasks.get(curId);
    }

    @Override
    public void deleteTask(int curId) throws IOException, ManagerSaveException {
        inMemoryHistoryManager.remove(curId);
        tasks.remove(curId);
        System.out.println("\nЗадача успешно удалена.");
    }

    @Override
    public void updateTask(Task task) throws IOException, ManagerSaveException {
        tasks.put(task.getId(), task);
    }

    @Override
    public void createNewEpic(Epic epic) throws IOException, ManagerSaveException {
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
    public void deleteAllEpics() throws IOException, ManagerSaveException {
        for (Integer x : subtasks.keySet()) {
            inMemoryHistoryManager.remove(x);
        }
        for (Integer x : epics.keySet()) {
            inMemoryHistoryManager.remove(x);
        }
        subtasks.clear();
        epics.clear();
        System.out.println("\nВсе эпики успешно удалены.");
    }

    @Override
    public Epic getEpicById(int curId) throws IOException, ManagerSaveException {
        inMemoryHistoryManager.addTask(epics.get(curId));
        return epics.get(curId);
    }

    @Override
    public void deleteEpic(int curId) throws IOException, ManagerSaveException {

        for (Integer curEpicSubtaskId : epics.get(curId).getSubtaskIds()) {
            subtasks.remove(curEpicSubtaskId);
            inMemoryHistoryManager.remove(curEpicSubtaskId);
        }
        inMemoryHistoryManager.remove(curId);
        epics.remove(curId);
        System.out.println("\nЭпик и его подзадачи успешно удалены.");
    }

    @Override
    public void updateEpic(Epic epic) throws IOException, ManagerSaveException {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createNewSubtask(Subtask subtask) throws IOException, ManagerSaveException {
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
    public void deleteAllSubtasks() throws IOException, ManagerSaveException {
        for (Subtask subtask : subtasks.values())
            if (!epics.get(subtask.getEpicId()).getSubtaskIds().isEmpty()) {
                epics.get(subtask.getEpicId()).getSubtaskIds().clear();
                setEpicStatus(epics.get(subtask.getEpicId()));
            }
        for (Integer x : subtasks.keySet()) {
            inMemoryHistoryManager.remove(x);
        }
        subtasks.clear();
        System.out.println("\nВсе подзадачи успешно удалены.");
    }

    @Override
    public Subtask getSubtaskById(int curId) throws IOException, ManagerSaveException {
        inMemoryHistoryManager.addTask(subtasks.get(curId));
        return subtasks.get(curId);
    }

    @Override
    public void deleteSubtask(int curId) throws IOException, ManagerSaveException {
        inMemoryHistoryManager.remove(curId);
        Epic curEpic = epics.get(subtasks.get(curId).getEpicId());
        curEpic.getSubtaskIds().remove((Integer) curId);
        subtasks.remove(curId);
        setEpicStatus(curEpic);
        System.out.println("\nПодзадача успешно удалена.");
    }

    @Override
    public void updateSubtask(Subtask subtask) throws IOException, ManagerSaveException {
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

    @Override
    public List<Task> getHistory() {
        return getInMemoryHistoryManager().getHistory();
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

