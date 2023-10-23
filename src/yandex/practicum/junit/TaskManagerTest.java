package yandex.practicum.junit;

import org.junit.jupiter.api.Test;
import yandex.practicum.service.TaskManager;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Status;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    TaskManager taskManager;

    @Test
    public void createNewTaskTest() {
        Task task1 = new Task("task1", "Description task1", "35", "19.05.2023_12:00");
        Task task2 = new Task("task2", "Description task2", "35", "19.05.2023_12:10");
        taskManager.createNewTask(task1);
        taskManager.createNewTask(task2);
        final Task savedTask1 = taskManager.getTaskById(task1.getId());

        assertNotNull(task1);
        assertEquals(task1, savedTask1);

        final Map<Integer, Task> tasks = taskManager.getTasks();
        final Set<Task> prioritizedTasks = taskManager.getPrioritizedTasks();

        assertNotNull(tasks);
        assertNotNull(prioritizedTasks);
        assertEquals(1, tasks.size());
        assertEquals(1, prioritizedTasks.size());
        assertEquals(task1, tasks.get(1));
    }

    @Test
    public void getTasksTest() {
        Task task1 = new Task("task1", "Description task1", "35", "19.05.2023_12:00");
        taskManager.createNewTask(task1);
        final Map<Integer, Task> tasks = taskManager.getTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(task1, tasks.get(1));
    }

    @Test
    public void getPrioritizedTasksTest() {
        Task task1 = new Task("task1", "Description task1", "35", "20.05.2023_12:00");
        Task task2 = new Task("task2", "Description task2", "15", "19.05.2023_12:10");
        taskManager.createNewTask(task1);
        taskManager.createNewTask(task2);
        final Set<Task> prioritizedTasks = taskManager.getPrioritizedTasks();

        assertNotNull(prioritizedTasks);
        assertEquals(2, prioritizedTasks.size());
    }

    @Test
    public void deleteAllTasksTest() {
        Task task1 = new Task("task1", "Description task1", "35", "19.05.2023_12:00");
        taskManager.createNewTask(task1);
        taskManager.deleteAllTasks();
        final Map<Integer, Task> tasks = taskManager.getTasks();

        assertEquals(0, tasks.size());
    }

    @Test
    public void getTaskByIdTest() {
        Task task1 = new Task("task1", "Description task1", "35", "19.05.2023_12:00");
        taskManager.createNewTask(task1);
        final Task savedTask1 = taskManager.getTaskById(task1.getId());

        assertNotNull(savedTask1);
        assertEquals(task1, savedTask1);
    }

    @Test
    public void deleteTaskTest() {
        Task task1 = new Task("task1", "Description task1", "35", "19.05.2023_12:00");
        taskManager.createNewTask(task1);
        taskManager.deleteTask(1);
        final Task savedTask1 = taskManager.getTaskById(task1.getId());

        assertNull(savedTask1);
    }

    @Test
    public void updateTaskTest() {
        Task task1 = new Task("task1", "Description task1", "35", "19.05.2023_12:00");
        taskManager.createNewTask(task1);
        task1.setStatus(Status.DONE);
        taskManager.updateTask(task1);
        final Task savedTask1 = taskManager.getTaskById(task1.getId());

        assertNotNull(savedTask1);
        assertEquals(task1, savedTask1);
    }

    @Test
    public void createNewEpicTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        final Epic savedEpic1 = taskManager.getEpicById(epic1.getId());

        assertNotNull(savedEpic1);
        assertEquals(epic1, savedEpic1);

        final Map<Integer, Epic> epics = taskManager.getEpics();

        assertNotNull(epics);
        assertEquals(1, epics.size());
        assertEquals(epic1, epics.get(1));
        assertEquals(Status.NEW, epic1.getStatus());
    }

    @Test
    public void getEpicsTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        final Map<Integer, Epic> epics = taskManager.getEpics();

        assertNotNull(epics);
        assertEquals(1, epics.size());
        assertEquals(epic1, epics.get(1));
    }

    @Test
    public void deleteAllEpicsTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        Subtask subtask1 = new Subtask("sub task1", "Description sub task1", "55",
                "19.05.2023_12:00", 1);
        taskManager.createNewSubtask(subtask1);
        taskManager.deleteAllEpics();
        final Map<Integer, Epic> epics = taskManager.getEpics();
        final Map<Integer, Subtask> subtasks = taskManager.getSubtasks();

        assertEquals(0, epics.size());
        assertEquals(0, subtasks.size());
    }

    @Test
    public void getEpicByIdTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        final Epic savedEpic1 = taskManager.getEpicById(epic1.getId());

        assertNotNull(savedEpic1);
        assertEquals(epic1, savedEpic1);
    }

    @Test
    public void deleteEpicTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        taskManager.deleteEpic(1);
        final Epic savedEpic1 = taskManager.getEpicById(epic1.getId());

        assertNull(savedEpic1);
    }

    @Test
    public void updateEpicTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        epic1.setDescription("New description");
        taskManager.updateEpic(epic1);
        final Epic savedEpic1 = taskManager.getEpicById(epic1.getId());

        assertNotNull(savedEpic1);
        assertEquals(epic1, savedEpic1);
    }

    @Test
    public void createNewSubtaskTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        Subtask subtask1 = new Subtask("sub task1", "Description sub task1", "55",
                "19.05.2023_12:00", 1);
        Subtask subtask2 = new Subtask("sub task2", "Description sub task2", "55",
                "19.05.2023_12:20", 1);
        taskManager.createNewSubtask(subtask1);
        taskManager.createNewSubtask(subtask2);
        final Subtask savedSubtask1 = taskManager.getSubtaskById(subtask1.getId());

        assertNotNull(savedSubtask1);
        assertEquals(subtask1, savedSubtask1);

        final Map<Integer, Subtask> subtasks = taskManager.getSubtasks();

        assertNotNull(subtasks);
        assertNotNull(epic1);
        assertEquals(1, subtasks.size());
        assertEquals(subtask1, subtasks.get(2));
    }

    @Test
    public void getSubtasksTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        Subtask subtask1 = new Subtask("sub task1", "Description sub task1", "55",
                "19.05.2023_12:00", 1);
        taskManager.createNewSubtask(subtask1);
        final Map<Integer, Subtask> subtasks = taskManager.getSubtasks();

        assertNotNull(subtasks);
        assertEquals(1, subtasks.size());
        assertEquals(subtask1, subtasks.get(2));
    }

    @Test
    public void deleteAllSubtasksTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        Subtask subtask1 = new Subtask("sub task1", "Description sub task1", "55",
                "19.05.2023_12:00", 1);
        taskManager.createNewSubtask(subtask1);
        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);
        taskManager.deleteAllSubtasks();
        final Map<Integer, Subtask> subtasks = taskManager.getSubtasks();

        assertEquals(0, subtasks.size());
        assertEquals(Status.NEW, epic1.getStatus());
    }

    @Test
    public void getSubtaskByIdTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        Subtask subtask1 = new Subtask("sub task1", "Description sub task1", "55",
                "19.05.2023_12:00", 1);
        taskManager.createNewSubtask(subtask1);
        final Subtask savedSubtask1 = taskManager.getSubtaskById(subtask1.getId());

        assertNotNull(savedSubtask1);
        assertEquals(subtask1, savedSubtask1);
    }

    @Test
    public void deleteSubtaskTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        Subtask subtask1 = new Subtask("sub task1", "Description sub task1", "55",
                "19.05.2023_12:00", 1);
        taskManager.createNewSubtask(subtask1);
        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);
        taskManager.deleteSubtask(2);
        final Subtask savedSubtask1 = taskManager.getSubtaskById(subtask1.getId());

        assertNull(savedSubtask1);
        assertEquals(Status.NEW, epic1.getStatus());
    }

    @Test
    public void updateSubtaskTest() {
        Epic epic1 = new Epic("epic1", "Description epic1");
        taskManager.createNewEpic(epic1);
        Subtask subtask1 = new Subtask("sub task1", "Description sub task1", "55",
                "19.05.2023_12:00", 1);
        taskManager.createNewSubtask(subtask1);
        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);
        final Subtask savedSubtask1 = taskManager.getSubtaskById(subtask1.getId());

        assertNotNull(savedSubtask1);
        assertEquals(subtask1, savedSubtask1);
        assertEquals(Status.DONE, epic1.getStatus());
    }
}