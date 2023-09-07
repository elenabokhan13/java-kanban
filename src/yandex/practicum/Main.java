package yandex.practicum;

import yandex.practicum.service.*;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import static yandex.practicum.tasks.Status.DONE;
import static yandex.practicum.tasks.Status.IN_PROGRESS;

public class Main {

    public static void main(String[] args) {

        Task task1 = new Task("Z1", "11");
        Task task2 = new Task("Z2", "22");
        Epic epic1 = new Epic("E1", "11");
        Epic epic2 = new Epic("E2", "22");
        Subtask subtask1 = new Subtask("S1", "11", 3);
        Subtask subtask2 = new Subtask("S2", "22", 4);
        Subtask subtask3 = new Subtask("S3", "33", 4);

        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

        inMemoryTaskManager.createNewTask(task1);
        inMemoryTaskManager.createNewTask(task2);
        inMemoryTaskManager.createNewEpic(epic1);
        inMemoryTaskManager.createNewEpic(epic2);
        inMemoryTaskManager.createNewSubtask(subtask1);
        inMemoryTaskManager.createNewSubtask(subtask2);
        inMemoryTaskManager.createNewSubtask(subtask3);

        System.out.println();
        System.out.println("Списки всех задач, эпиков и подзадач");
        System.out.println();
        inMemoryTaskManager.printTaskList();
        inMemoryTaskManager.printEpicList();
        inMemoryTaskManager.printSubtaskList();

        System.out.println("\nВызов задачи [1]");
        inMemoryTaskManager.getTaskById(1);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов задачи [2]");
        inMemoryTaskManager.getTaskById(2);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов эпика [3]");
        inMemoryTaskManager.getEpicById(3);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов эпика [4]");
        inMemoryTaskManager.getEpicById(4);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов подзадачи [5]");
        inMemoryTaskManager.getSubtaskById(5);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов подзадачи [6]");
        inMemoryTaskManager.getSubtaskById(6);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов подзадачи [7]");
        inMemoryTaskManager.getSubtaskById(7);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов задачи [8]");
        inMemoryTaskManager.getTaskById(1);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов задачи [9]");
        inMemoryTaskManager.getTaskById(2);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов эпика [10]");
        inMemoryTaskManager.getEpicById(3);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        System.out.println("\nВызов эпика [11]");
        inMemoryTaskManager.getEpicById(4);
        System.out.println("\nИстория вызовов:");
        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());

        task1.setStatus(DONE);
        inMemoryTaskManager.updateTask(task1);
        task2.setStatus(IN_PROGRESS);
        inMemoryTaskManager.updateTask(task2);
        subtask1.setStatus(IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(subtask1);
        subtask2.setStatus(DONE);
        inMemoryTaskManager.updateSubtask(subtask2);
        subtask3.setStatus(DONE);
        inMemoryTaskManager.updateSubtask(subtask3);

        System.out.println();
        System.out.println("\nПосле обновления статусов");
        System.out.println();
        inMemoryTaskManager.printTaskList();
        inMemoryTaskManager.printEpicList();
        inMemoryTaskManager.printSubtaskList();

        inMemoryTaskManager.deleteTask(task1.getId());
        inMemoryTaskManager.deleteEpic(epic1.getId());

        System.out.println();
        System.out.println("\nПосле удаления первой задачи и первого эпика");
        System.out.println();
        inMemoryTaskManager.printTaskList();
        inMemoryTaskManager.printEpicList();
        inMemoryTaskManager.printSubtaskList();
    }
}

