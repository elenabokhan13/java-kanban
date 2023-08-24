import tool.service.Manager;
import tool.tasks.Epic;
import tool.tasks.Subtask;
import tool.tasks.Task;

import static tool.tasks.Status.DONE;
import static tool.tasks.Status.IN_PROGRESS;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Z1", "11");
        Task task2 = new Task("Z2", "22");
        Epic epic1 = new Epic("E1", "11");
        Epic epic2 = new Epic("E2", "22");
        Subtask subtask1 = new Subtask("S1", "11", 3);
        Subtask subtask2 = new Subtask("S2", "22", 4);
        Subtask subtask3 = new Subtask("S3", "33", 4);

        manager.createNewTask(task1);
        manager.createNewTask(task2);
        manager.createNewEpic(epic1);
        manager.createNewEpic(epic2);
        manager.createNewSubtask(subtask1);
        manager.createNewSubtask(subtask2);
        manager.createNewSubtask(subtask3);

        System.out.println();
        System.out.println("Списки всех задач, эпиков и подзадач");
        System.out.println();
        manager.printTaskList();
        manager.printEpicList();
        manager.printSubtaskList();

        task1.setStatus(DONE);
        manager.renewTask(task1);
        task2.setStatus(IN_PROGRESS);
        manager.renewTask(task2);
        subtask1.setStatus(IN_PROGRESS);
        manager.renewSubtask(subtask1);
        subtask2.setStatus(DONE);
        manager.renewSubtask(subtask2);
        subtask3.setStatus(DONE);
        manager.renewSubtask(subtask3);

        System.out.println();
        System.out.println("После обновления статусов");
        System.out.println();
        manager.printTaskList();
        manager.printEpicList();
        manager.printSubtaskList();

        manager.deleteTask(task1.getId());
        manager.deleteEpic(epic1.getId());

        System.out.println();
        System.out.println("После удаления первой задачи и первого эпика");
        System.out.println();
        manager.printTaskList();
        manager.printEpicList();
        manager.printSubtaskList();
    }
}
