package yandex.practicum;

import yandex.practicum.server.KVServer;
import yandex.practicum.service.HttpTaskManager;
import yandex.practicum.service.Managers;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Status;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        KVServer kvServer = new KVServer();
        kvServer.start();

        HttpTaskManager manager = (HttpTaskManager) Managers.getDefault();

        Task task1 = new Task("task1", "Description task1", "35", "20.05.2023_12:00");
        Task task2 = new Task("task2", "Description task2", "15", "19.05.2023_12:10");
        Task task3 = new Task("task3", "Description task2", "15", "19.05.2023_12:15");
        Epic epic1 = new Epic("epic1", "Description epic1");
        Epic epic2 = new Epic("epic2", "Description epic2");
        Subtask subtask1 = new Subtask("sub task1", "Description sub task1", "55",
                "21.05.2023_13:00", 3);
        Subtask subtask2 = new Subtask("sub task2", "Description sub task2", "25",
                "23.05.2023_12:00", 3);
        Subtask subtask3 = new Subtask("sub task3", "Description sub task3", "10",
                "20.05.2023_11:00", 3);
        Subtask subtask4 = new Subtask("sub task4", "Description sub task3", "10",
                "20.05.2023_11:05", 3);

        manager.createNewTask(task1);
        manager.createNewTask(task2);
        manager.createNewTask(task3);
        manager.createNewEpic(epic1);
        manager.createNewEpic(epic2);
        manager.createNewSubtask(subtask1);
        manager.createNewSubtask(subtask2);
        manager.createNewSubtask(subtask3);
        manager.createNewSubtask(subtask4);

        System.out.println(manager.getEpics());
        System.out.println(manager.getPrioritizedTasks());

        System.out.println("\nВызов #1");
        System.out.println(manager.getTaskById(1));
        System.out.println("\nИстория вызовов:");
        System.out.println(manager.getHistory());

        System.out.println("\nВызов #2");
        System.out.println(manager.getSubtaskById(7));
        System.out.println("\nИстория вызовов:");
        System.out.println(manager.getHistory());

        System.out.println("\nВызов #3");
        System.out.println(manager.getSubtaskById(5));
        System.out.println("\nИстория вызовов:");
        System.out.println(manager.getHistory());

        System.out.println("\nВызов #4");
        task1.setStatus(Status.DONE);
        manager.updateTask(task1);
        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);
        System.out.println(manager.getTaskById(1));
        System.out.println("\nИстория вызовов:");
        System.out.println(manager.getHistory());

        System.out.println(manager.getKvTaskClient().load(manager.getKvTaskClient().getAPI_TOKEN()));
    }
}


