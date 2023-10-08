package yandex.practicum;

import yandex.practicum.service.*;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ManagerSaveException {

        Task task1 = new Task("Z1", "11");
        Task task2 = new Task("Z2", "22");
        Epic epic1 = new Epic("E1", "11");
        Epic epic2 = new Epic("E2", "22");
        Subtask subtask1 = new Subtask("S1", "11", 3);
        Subtask subtask2 = new Subtask("S2", "22", 3);
        Subtask subtask3 = new Subtask("S3", "33", 3);

        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

        inMemoryTaskManager.createNewTask(task1);
        inMemoryTaskManager.createNewTask(task2);
        inMemoryTaskManager.createNewEpic(epic1);
        inMemoryTaskManager.createNewEpic(epic2);
        inMemoryTaskManager.createNewSubtask(subtask1);
        inMemoryTaskManager.createNewSubtask(subtask2);
        inMemoryTaskManager.createNewSubtask(subtask3);

        System.out.println("\nВызов #1");
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #2");
        System.out.println(inMemoryTaskManager.getSubtaskById(7));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #3");
        System.out.println(inMemoryTaskManager.getSubtaskById(5));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #4");
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #5");
        System.out.println(inMemoryTaskManager.getTaskById(2));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #6");
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #7");
        System.out.println(inMemoryTaskManager.getSubtaskById(5));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #8");
        System.out.println(inMemoryTaskManager.getSubtaskById(6));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #9");
        System.out.println(inMemoryTaskManager.getEpicById(3));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #10");
        System.out.println(inMemoryTaskManager.getEpicById(4));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("\nВызов #11");
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.deleteTask(task1.getId());
        System.out.println("\nПосле удаления первой задачи");
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.deleteEpic(epic1.getId());
        System.out.println("\nПосле удаления первого эпика (с сабтасками)");
        System.out.println(inMemoryTaskManager.getHistory());
    }
}

