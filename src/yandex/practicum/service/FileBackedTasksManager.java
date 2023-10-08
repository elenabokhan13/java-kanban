package yandex.practicum.service;

import yandex.practicum.exceptions.ManagerSaveException;
import yandex.practicum.tasks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static yandex.practicum.tasks.TypeOfTask.SUBTASK;
import static yandex.practicum.tasks.TypeOfTask.TASK;

public class FileBackedTasksManager extends InMemoryTaskManager {

    // Я перенесла этот мейн в основной мейн, изначально сделала здесь, потому что так было напсиано у нас в
    // ТЗ (дословно): "Для этого создайте метод static void main(String[] args) в классе FileBackedTasksManager
    // и реализуйте небольшой сценарий". Тоже удивилась, почему здесь.
    // Сокращения с "cur" по возможности тоже убрала, спасибо за совет, я запомню. Просто наш наставник постоянно
    // говорит, что так принято делать и чтобы мы так делали :)
    // В методе loadFromFile я добавила switch-case, но совсем отказаться от if не получается, т.к. в файле, который
    // считываем микс строк и не во всех есть таски, где пуста, а где-то история, которую тоже надо "прочитать"

    final static String HEADER = "id,type,name,status,description,epic\n";
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

    public void save() throws IOException, ManagerSaveException {
        Writer fileWriter = new FileWriter(file);
        Map<Integer, Task> currentTasks;
        currentTasks = super.getTasks();
        fileWriter.write(HEADER);
        for (Task currentTask : currentTasks.values()) {
            try {
                fileWriter.write(toString(currentTask) + "\n");
            } catch (IOException e) {
                throw new ManagerSaveException("Произошла ошибка при сохранении задач.");
            }
        }
        Map<Integer, Subtask> currentSubtasks;
        currentSubtasks = super.getSubtasks();
        for (Subtask curSubtask : currentSubtasks.values()) {
            try {
                fileWriter.write(toString(curSubtask) + "\n");
            } catch (IOException e) {
                throw new ManagerSaveException("Произошла ошибка при сохранении подзадач.");
            }
        }
        Map<Integer, Epic> currentEpics;
        currentEpics = super.getEpics();
        for (Epic currentEpic : currentEpics.values()) {
            try {
                fileWriter.write(toString(currentEpic) + "\n");
            } catch (IOException e) {
                throw new ManagerSaveException("Произошла ошибка при сохранении эпиков.");
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
            if (line.contains("Description ")) {
                String[] details = line.split(",");
                TypeOfTask currentType = TypeOfTask.valueOf(details[1]);
                switch (currentType) {
                    case TASK -> manager.tasks.put(manager.fromString(line).getId(), manager.fromString(line));
                    case SUBTASK -> manager.subtasks.put(manager.fromString(line).getId(),
                            (Subtask) manager.fromString(line));
                    case EPIC -> manager.epics.put(manager.fromString(line).getId(), (Epic) manager.fromString(line));
                }
            } else if (line.contains("id")) {
            } else if (line.isBlank()) {
            } else {
                List<Integer> currentList;
                currentList = historyFromString(line);
                for (Integer currentCount : currentList) {
                    if (manager.tasks.containsKey(currentCount)) {
                        manager.inMemoryHistoryManager.addTask(manager.tasks.get(currentCount));
                    } else if (manager.subtasks.containsKey(currentCount)) {
                        manager.inMemoryHistoryManager.addTask(manager.subtasks.get(currentCount));
                    } else if (manager.epics.containsKey(currentCount)) {
                        manager.inMemoryHistoryManager.addTask(manager.epics.get(currentCount));
                    }
                }
            }
        }
        return manager;
    }

    private String toString(Task task) throws IOException {
        String currentTask = task.getId() + "," + task.getTypeOfTask() + "," + task.getName() + ","
                + task.getStatus() + "," + task.getDescription() + ",";
        if (task.getTypeOfTask() == SUBTASK) {
            Subtask subtask = (Subtask) task;
            currentTask = currentTask + subtask.getEpicId();
        }
        return currentTask;
    }

    private Task fromString(String value) {
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

    private static String historyToString(HistoryManager manager) {
        List<Task> currentHistory;
        currentHistory = manager.getHistory();
        String currentLine = "";
        for (Task task : currentHistory) {
            currentLine = currentLine + task.getId() + ",";
        }
        currentLine = currentLine.substring(0, currentLine.length() - 1);
        return currentLine;
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> currentList = new ArrayList<>();
        String[] currentValue = value.split(",");
        for (String currentString : currentValue) {
            currentList.add(Integer.parseInt(currentString));
        }
        return currentList;
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
    public Task getTaskById(int newId) throws IOException, ManagerSaveException {
        super.getTaskById((newId));
        save();
        return tasks.get(newId);
    }

    @Override
    public void deleteTask(int newId) throws IOException, ManagerSaveException {
        super.deleteTask(newId);
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
    public Epic getEpicById(int newId) throws IOException, ManagerSaveException {
        super.getEpicById(newId);
        save();
        return epics.get(newId);
    }

    @Override
    public void deleteEpic(int newId) throws IOException, ManagerSaveException {
        super.deleteEpic(newId);
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
    public Subtask getSubtaskById(int newId) throws IOException, ManagerSaveException {
        super.getSubtaskById(newId);
        save();
        return subtasks.get(newId);
    }

    @Override
    public void deleteSubtask(int newId) throws IOException, ManagerSaveException {
        super.deleteSubtask(newId);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) throws IOException, ManagerSaveException {
        super.updateSubtask(subtask);
        save();
    }
}


