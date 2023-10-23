package yandex.practicum.service;

import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Status;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    protected final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    protected int currentId = 1;

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }

    @Override
    public void createNewTask(Task task) {
        task.setId(currentId);
        for (Task task_current : prioritizedTasks) {
            if (task.getEndTime().isBefore(task_current.getStartTime()) || task.getStartTime().isAfter(task_current
                    .getEndTime())) {
            } else {
                System.out.println("Задача " + task.getName() + " не может быть создана, т.к. она наслаивается на " +
                        "другую задачу. Измените ее сроки.");
                return;
            }
        }
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
        System.out.println("Задача создана.");
        currentId++;
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public void deleteAllTasks() {
        for (Integer x : tasks.keySet()) {
            inMemoryHistoryManager.remove(x);
        }

        for (Task task : tasks.values()) {
            prioritizedTasks.remove(task);
        }
        tasks.clear();
        prioritizedTasks.clear();
        System.out.println("\nВсе задачи успешно удалены.");
    }

    @Override
    public Task getTaskById(int newId) {
        inMemoryHistoryManager.addTask(tasks.get(newId));
        return tasks.get(newId);
    }

    @Override
    public void deleteTask(int newId) {
        inMemoryHistoryManager.remove(newId);
        prioritizedTasks.remove(tasks.get(newId));
        tasks.remove(newId);
        System.out.println("\nЗадача успешно удалена.");
    }

    @Override
    public void updateTask(Task task) {
        for (Task task_current : prioritizedTasks) {
            if (task.getStartTime().isEqual(task_current.getStartTime()) && task.getEndTime().isEqual(task_current.getEndTime())) {
                tasks.put(task.getId(), task);
                prioritizedTasks.add(task);
                return;
            }
            if (task.getEndTime().isBefore(task_current.getStartTime()) || task.getStartTime().isAfter(task_current.getEndTime())) {

            } else {
                System.out.println("Задача " + task.getName() + " не может быть обновлена, т.к. она наслаивается на другую задачу. Измените ее сроки.");

                return;
            }
        }
    }

    @Override
    public void createNewEpic(Epic epic) {
        epic.setId(currentId);
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);
        System.out.println("Эпик создан.");
        currentId++;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public void deleteAllEpics() {
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
    public Epic getEpicById(int newId) {
        inMemoryHistoryManager.addTask(epics.get(newId));
        return epics.get(newId);
    }

    @Override
    public void deleteEpic(int newId) {

        for (Integer curEpicSubtaskId : epics.get(newId).getSubtaskIds()) {
            subtasks.remove(curEpicSubtaskId);
            inMemoryHistoryManager.remove(curEpicSubtaskId);
        }
        inMemoryHistoryManager.remove(newId);
        epics.remove(newId);
        System.out.println("\nЭпик и его подзадачи успешно удалены.");
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        subtask.setId(currentId);
        for (Task task_current : prioritizedTasks) {
            if (subtask.getEndTime().isBefore(task_current.getStartTime()) || subtask.getStartTime()
                    .isAfter(task_current.getEndTime())) {
            } else {
                System.out.println("Подзадача " + subtask.getName() + " не может быть создана, т.к. она наслаивается " +
                        "на другую задачу. Измените ее сроки.");
                return;
            }
        }
        subtasks.put(subtask.getId(), subtask);
        prioritizedTasks.add(subtask);
        Epic curEpic = epics.get(subtask.getEpicId());
        curEpic.getSubtaskIds().add(subtask.getId());
        setEpicStatus(curEpic);
        setEpicTimes(curEpic);
        System.out.println("Подзадача создана.");
        currentId++;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public void deleteAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            prioritizedTasks.remove(subtask);
            if (!epics.get(subtask.getEpicId()).getSubtaskIds().isEmpty()) {
                epics.get(subtask.getEpicId()).getSubtaskIds().clear();
                setEpicStatus(epics.get(subtask.getEpicId()));
                setEpicTimes(epics.get(subtask.getEpicId()));
            }
        }
        for (Integer x : subtasks.keySet()) {
            inMemoryHistoryManager.remove(x);
        }
        subtasks.clear();
        System.out.println("\nВсе подзадачи успешно удалены.");
    }

    @Override
    public Subtask getSubtaskById(int newId) {
        inMemoryHistoryManager.addTask(subtasks.get(newId));
        return subtasks.get(newId);
    }

    @Override
    public void deleteSubtask(int newId) {
        inMemoryHistoryManager.remove(newId);
        Epic curEpic = epics.get(subtasks.get(newId).getEpicId());
        curEpic.getSubtaskIds().remove((Integer) newId);
        prioritizedTasks.add(subtasks.get(newId));
        subtasks.remove(newId);
        setEpicStatus(curEpic);
        setEpicTimes(curEpic);
        System.out.println("\nПодзадача успешно удалена.");
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        for (Task task_current : prioritizedTasks) {
            if (subtask.getStartTime().isEqual(task_current.getStartTime()) && subtask.getEndTime()
                    .isEqual(task_current.getEndTime())) {
                subtasks.put(subtask.getId(), subtask);
                prioritizedTasks.add(subtask);
                Epic curEpic = epics.get(subtask.getEpicId());
                setEpicStatus(curEpic);
                setEpicTimes(curEpic);
                return;
            }
            if (subtask.getEndTime().isBefore(task_current.getStartTime()) || subtask.getStartTime()
                    .isAfter(task_current.getEndTime())) {
            } else {
                System.out.println("Подзадача " + subtask.getName() + " не может быть обновлена, т.к. она " +
                        "наслаивается на другую задачу. Измените ее сроки.");
                return;
            }
        }
    }

    @Override
    public void printSubtasksInEpicList(Epic epic) {
        for (Integer newId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(newId);
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

    private void setEpicStartTime(Epic epic) {
        LocalDateTime firstTime;
        if (epic.getSubtaskIds().size() != 0) {
            firstTime = subtasks.get(epic.getSubtaskIds().get(0)).getStartTime();
            for (Integer i : epic.getSubtaskIds()) {
                LocalDateTime time = subtasks.get(i).getStartTime();
                if (time.isBefore(firstTime)) {
                    firstTime = time;
                }
            }
            epic.setStartTime(firstTime);
        }
    }

    private void setEpicEndTime(Epic epic) {
        LocalDateTime lastTime;
        if (epic.getSubtaskIds().size() != 0) {
            lastTime = subtasks.get(epic.getSubtaskIds().get(0)).getEndTime();
            for (Integer i : epic.getSubtaskIds()) {
                LocalDateTime time = subtasks.get(i).getEndTime();
                if (time.isAfter(lastTime)) {
                    lastTime = time;
                }
            }
            epic.setEndTime(lastTime);
        }
    }

    private void setEpicDuration(Epic epic) {
        if (epic.getSubtaskIds().size() != 0) {
            epic.setDuration(Duration.between(epic.getStartTime(), epic.getEndTime()));
        }
    }

    private void setEpicTimes(Epic epic) {
        setEpicStartTime(epic);
        setEpicEndTime(epic);
        setEpicDuration(epic);
    }
}

