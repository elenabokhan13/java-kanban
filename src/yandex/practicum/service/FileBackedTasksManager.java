package yandex.practicum.service;

import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Status;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.io.*;
import java.util.*;

import static yandex.practicum.service.TypeOfTask.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    public static void main(String[] args) throws IOException, ManagerSaveException {

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
        System.out.println(manager1.tasks);
        System.out.println(manager1.subtasks);
        System.out.println(manager1.epics);
    }

    private TypeOfTask typeOfTask;
    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public TypeOfTask getTypeOfTask() {
        return typeOfTask;
    }

    public void setTypeOfTask(TypeOfTask typeOfTask) {
        this.typeOfTask = typeOfTask;
    }

    public void save() throws IOException, ManagerSaveException {
        Writer fileWriter = new FileWriter(file);
        Map<Integer, Task> curTasks;
        curTasks = super.getTasks();
        fileWriter.write("id,type,name,status,description,epic\n");
        for (Task curTask : curTasks.values()) {
            try {
                fileWriter.write(toString(curTask) + "\n");
            } catch (IOException exception) {
                throw new ManagerSaveException();
            }
        }
        Map<Integer, Subtask> curSubtasks;
        curSubtasks = super.getSubtasks();
        for (Subtask curSubtask : curSubtasks.values()) {
            try {
                fileWriter.write(toString(curSubtask) + "\n");
            } catch (IOException exception) {
                throw new ManagerSaveException();
            }
        }
        Map<Integer, Epic> curEpics;
        curEpics = super.getEpics();
        for (Epic curEpic : curEpics.values()) {
            try {
                fileWriter.write(toString(curEpic) + "\n");
                //        fileWriter.close();
            } catch (IOException exception) {
                throw new ManagerSaveException();
            }
        }
        fileWriter.write("\n");
        try {
            fileWriter.write(historyToString(super.inMemoryHistoryManager));
        } catch (NoSuchElementException exception) {
        }
        fileWriter.close();
    }

    public static FileBackedTasksManager loadFromFile(File file) throws IOException {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);

        while (br.ready()) {
            String line = br.readLine();
            if (line.contains("SUBTASK")) {
                manager.subtasks.put(manager.fromString(line).getId(), (Subtask) manager.fromString(line));
            } else if (line.contains("TASK")) {
                manager.tasks.put(manager.fromString(line).getId(), manager.fromString(line));
            } else if (line.contains("EPIC")) {
                manager.epics.put(manager.fromString(line).getId(), (Epic) manager.fromString(line));
            } else if (line.contains("id,type,name")) {
            } else if (line.isBlank()) {
            } else {
                List<Integer> curList;
                curList = historyFromString(line);

                for (Integer curCount : curList) {
                    if (manager.tasks.containsKey(curCount)) {
                        manager.inMemoryHistoryManager.addTask(manager.tasks.get(curCount));
                    } else if (manager.subtasks.containsKey(curCount)) {
                        manager.inMemoryHistoryManager.addTask(manager.subtasks.get(curCount));
                    } else if (manager.epics.containsKey(curCount)) {
                        manager.inMemoryHistoryManager.addTask(manager.epics.get(curCount));
                    }
                }
            }
        }
        return manager;
    }

    public String toString(Task task) throws IOException {
        if (task.getClass() == Task.class) {
            setTypeOfTask(TASK);
        } else if (task.getClass() == Subtask.class) {
            setTypeOfTask(SUBTASK);
        } else {
            setTypeOfTask(EPIC);
        }
        String curTask = task.getId() + "," + getTypeOfTask() + "," + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + ",";
        if (getTypeOfTask() == SUBTASK) {
            Subtask subtask = (Subtask) task;
            curTask = curTask + subtask.getEpicId();
        }
        return curTask;
    }

    public Task fromString(String value) {
        String[] details = value.split(",");
        if (details[1].equals(TASK.toString())) {
            Task task = new Task(details[2], details[4]);
            task.setId(Integer.parseInt(details[0]));
            switch (details[3]) {
                case "NEW" -> task.setStatus(Status.NEW);
                case "IN_PROGRESS" -> task.setStatus(Status.IN_PROGRESS);
                case "DONE" -> task.setStatus(Status.DONE);
            }
            return task;
        } else if (details[1].equals(SUBTASK.toString())) {
            Task task = new Subtask(details[2], details[4], Integer.parseInt(details[5]));
            task.setId(Integer.parseInt(details[0]));
            switch (details[3]) {
                case "NEW" -> task.setStatus(Status.NEW);
                case "IN_PROGRESS" -> task.setStatus(Status.IN_PROGRESS);
                case "DONE" -> task.setStatus(Status.DONE);
            }
            return task;
        } else {
            Task task = new Epic(details[2], details[4]);
            task.setId(Integer.parseInt(details[0]));
            switch (details[3]) {
                case "NEW" -> task.setStatus(Status.NEW);
                case "IN_PROGRESS" -> task.setStatus(Status.IN_PROGRESS);
                case "DONE" -> task.setStatus(Status.DONE);
            }
            return task;
        }
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> curHistory;
        curHistory = manager.getHistory();
        String curLine = "";
        for (Task task : curHistory) {
            curLine = curLine + task.getId() + ",";
        }
        curLine = curLine.substring(0, curLine.length() - 1);
        return curLine;
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> curList = new ArrayList<>();
        String[] curValue = value.split(",");
        for (String curString : curValue) {
            curList.add(Integer.parseInt(curString));
        }
        return curList;
    }

    @Override
    public void createNewTask(Task task) throws IOException, ManagerSaveException {
        super.createNewTask(task);
        save();
    }

    @Override
    public void deleteAllTasks() throws IOException, ManagerSaveException {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task getTaskById(int curId) throws IOException, ManagerSaveException {
        super.getTaskById((curId));
        save();
        return tasks.get(curId);
    }

    @Override
    public void deleteTask(int curId) throws IOException, ManagerSaveException {
        super.deleteTask(curId);
        save();
    }

    @Override
    public void updateTask(Task task) throws IOException, ManagerSaveException {
        super.updateTask(task);
        save();
    }

    @Override
    public void createNewEpic(Epic epic) throws IOException, ManagerSaveException {
        super.createNewEpic(epic);
        save();
    }

    @Override
    public void deleteAllEpics() throws IOException, ManagerSaveException {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Epic getEpicById(int curId) throws IOException, ManagerSaveException {
        super.getEpicById(curId);
        save();
        return epics.get(curId);
    }

    @Override
    public void deleteEpic(int curId) throws IOException, ManagerSaveException {
        super.deleteEpic(curId);
        save();
    }


    @Override
    public void updateEpic(Epic epic) throws IOException, ManagerSaveException {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void createNewSubtask(Subtask subtask) throws IOException, ManagerSaveException {
        super.createNewSubtask(subtask);
        save();
    }

    @Override
    public void deleteAllSubtasks() throws IOException, ManagerSaveException {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Subtask getSubtaskById(int curId) throws IOException, ManagerSaveException {
        super.getSubtaskById(curId);
        save();
        return subtasks.get(curId);
    }

    @Override
    public void deleteSubtask(int curId) throws IOException, ManagerSaveException {
        super.deleteSubtask(curId);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) throws IOException, ManagerSaveException {
        super.updateSubtask(subtask);
        save();
    }
}


