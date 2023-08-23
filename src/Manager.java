import java.util.HashMap;

public class Manager {
    int currentId = 1;
    HashMap<Integer, Task> taskList = new HashMap<>();
    HashMap<Integer, Epic> epicList = new HashMap<>();
    HashMap<Integer, Subtask> subtaskList = new HashMap<>();

    public void createNewTask(Task task) {
        task.id = currentId;
        taskList.put(task.id, task);
        System.out.println("Задача создана.");
        currentId++;
    }

    public void printTaskList() {
        for (Task task : taskList.values()) {
            System.out.println(task);
        }
    }

    public void deleteAllTasks() {
        taskList.clear();
        System.out.println("Все задачи успешно удалены.");
    }

    public void getTaskById(int curId) {
        System.out.println(taskList.get(curId));
    }

    public void deleteTask(int curId) {
        taskList.remove(curId);
        System.out.println("Задача успешно удалена.");
    }

    public void renewTask(Task task) {
        taskList.put(task.id, task);
    }

    public void createNewEpic(Epic epic) {
        epic.id = currentId;
        epicList.put(epic.id, epic);
        epic.status = "NEW";
        setEpicStatus(epic);
        System.out.println("Эпик создан.");
        currentId++;
        addEpicIdToSubtask(epic);
    }

    public void printEpicList() {
        for (Epic epic : epicList.values()) {
            System.out.println(epic);
        }
    }

    public void deleteAllEpics() {
        epicList.clear();
        System.out.println("Все эпики успешно удалены.");
    }

    public void getEpicById(int curId) {
        System.out.println(epicList.get(curId));
    }

    public void deleteEpic(int curId) {
        epicList.remove(curId);
        System.out.println("Эпик успешно удален.");
    }

    public void renewEpic(Epic epic) {
        epicList.put(epic.id, epic);
        setEpicStatus(epic);
        addEpicIdToSubtask(epic);
    }

    public void createNewSubtask(Subtask subtask) {
        subtask.id = currentId;
        subtaskList.put(subtask.id, subtask);
        System.out.println("Подзадача создана.");
        currentId++;
    }

    public void printSubtaskList() {
        for (Subtask subtask : subtaskList.values()) {
            System.out.println(subtask);
        }
    }

    public void deleteAllSubtasks() {
        subtaskList.clear();
        System.out.println("Все подзадачи успешно удалены.");
    }

    public void getSubtaskById(int curId) {
        System.out.println(subtaskList.get(curId));
    }

    public void deleteSubtask(int curId) {
        subtaskList.remove(curId);
        System.out.println("Подзадача успешно удалена.");
    }

    public void renewSubtask(Subtask subtask) {
        subtaskList.put(subtask.id, subtask);
        Epic epic = epicList.get(subtask.epicId);
        setEpicStatus(epic);
    }

    public void printSubtasksInEpicList(Epic epic) {
        for (Integer curId : epic.subtasksList) {
            Subtask subtask = subtaskList.get(curId);
            System.out.println(subtask);
        }
    }

    private void setEpicStatus(Epic epic) {
        String currentStatus = "NULL_STATUS";
        for (Integer subtaskId : epic.subtasksList) {
            Subtask subtask = subtaskList.get(subtaskId);
            if (subtask.status.equals("NEW")) {
                if (currentStatus.equals("NULL_STATUS") || currentStatus.equals("NEW")) {
                    currentStatus = "NEW";
                } else {
                    currentStatus = "IN_PROGRESS";
                    break;
                }
            } else if (subtask.status.equals("IN_PROGRESS")) {
                currentStatus = "IN_PROGRESS";
                break;
            } else if (subtask.status.equals("DONE")) {
                if (currentStatus.equals("NULL_STATUS") || currentStatus.equals("DONE")) {
                    currentStatus = "DONE";
                } else {
                    currentStatus = "IN_PROGRESS";
                    break;
                }
            }
            epic.status = currentStatus;
        }
    }

    private void addEpicIdToSubtask(Epic epic) {
        for (Integer subtaskId : epic.subtasksList) {
            Subtask subtask = subtaskList.get(subtaskId);
            subtask.epicId = epic.id;
        }
    }
}

