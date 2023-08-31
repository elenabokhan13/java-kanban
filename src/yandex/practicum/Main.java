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

        Managers.getDefault().createNewTask(task1);
        Managers.getDefault().createNewTask(task2);
        Managers.getDefault().createNewEpic(epic1);
        Managers.getDefault().createNewEpic(epic2);
        Managers.getDefault().createNewSubtask(subtask1);
        Managers.getDefault().createNewSubtask(subtask2);
        Managers.getDefault().createNewSubtask(subtask3);

        System.out.println();
        System.out.println("Списки всех задач, эпиков и подзадач");
        System.out.println();
        Managers.getDefault().printTaskList();
        Managers.getDefault().printEpicList();
        Managers.getDefault().printSubtaskList();

        System.out.println("\nВызов задачи [1]");
        Managers.getDefault().getTaskById(1);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов задачи [2]");
        Managers.getDefault().getTaskById(2);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов эпика [3]");
        Managers.getDefault().getEpicById(3);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов эпика [4]");
        Managers.getDefault().getEpicById(4);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов подзадачи [5]");
        Managers.getDefault().getSubtaskById(5);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов подзадачи [6]");
        Managers.getDefault().getSubtaskById(6);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов подзадачи [7]");
        Managers.getDefault().getSubtaskById(7);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов задачи [8]");
        Managers.getDefault().getTaskById(1);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов задачи [9]");
        Managers.getDefault().getTaskById(2);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов эпика [10]");
        Managers.getDefault().getEpicById(3);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        System.out.println("\nВызов эпика [11]");
        Managers.getDefault().getEpicById(4);
        System.out.println("\nИстория вызовов:");
        Managers.getDefaultHistory().getHistory();

        task1.setStatus(DONE);
        Managers.getDefault().updateTask(task1);
        task2.setStatus(IN_PROGRESS);
        Managers.getDefault().updateTask(task2);
        subtask1.setStatus(IN_PROGRESS);
        Managers.getDefault().updateSubtask(subtask1);
        subtask2.setStatus(DONE);
        Managers.getDefault().updateSubtask(subtask2);
        subtask3.setStatus(DONE);
        Managers.getDefault().updateSubtask(subtask3);

        System.out.println();
        System.out.println("\nПосле обновления статусов");
        System.out.println();
        Managers.getDefault().printTaskList();
        Managers.getDefault().printEpicList();
        Managers.getDefault().printSubtaskList();

        Managers.getDefault().deleteTask(task1.getId());
        Managers.getDefault().deleteEpic(epic1.getId());

        System.out.println();
        System.out.println("\nПосле удаления первой задачи и первого эпика");
        System.out.println();
        Managers.getDefault().printTaskList();
        Managers.getDefault().printEpicList();
        Managers.getDefault().printSubtaskList();
    }
}

