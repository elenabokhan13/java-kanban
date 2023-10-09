package yandex.practicum;

import yandex.practicum.service.FileBackedTasksManager;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Status;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static yandex.practicum.service.FileBackedTasksManager.loadFromFile;

public class Main {

    public static void main(String[] args) throws IOException {

        File file = new File("data.csv");

        FileBackedTasksManager manager = new FileBackedTasksManager(file);

        Task task1 = new Task("task1", "Description task1");
        Task task2 = new Task("task2", "Description task2");
        Epic epic1 = new Epic("epic1", "Description epic1");
        Epic epic2 = new Epic("epic2", "Description epic2");
        Subtask subtask1 = new Subtask("sub task1", "Description sub task1", 3);
        Subtask subtask2 = new Subtask("sub task2", "Description sub task2", 3);
        Subtask subtask3 = new Subtask("sub task3", "Description sub task3", 3);

        manager.createNewTask(task1);
        manager.createNewTask(task2);
        manager.createNewEpic(epic1);
        manager.createNewEpic(epic2);
        manager.createNewSubtask(subtask1);
        manager.createNewSubtask(subtask2);
        manager.createNewSubtask(subtask3);

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

        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);

        while (br.ready()) {
            String line = br.readLine();
            System.out.println(line);
        }
        br.close();

        FileBackedTasksManager manager1 = loadFromFile(file);

        System.out.println("\nИстория из нового менеджера:");
        System.out.println(manager1.getHistory());
        System.out.println("\nЗадачи, записанные в новом менеджере:");
        System.out.println(manager1.getTasks());
        System.out.println(manager1.getSubtasks());
        System.out.println(manager1.getEpics());
    }
}


