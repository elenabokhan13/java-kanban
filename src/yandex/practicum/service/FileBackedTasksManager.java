package yandex.practicum.service;

import yandex.practicum.exceptions.ManagerSaveException;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;
import yandex.practicum.tasks.TypeOfTask;
import yandex.practicum.utils.CSVTaskUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final static String HEADER = "id,type,name,status,description,start_time,end_time,duration,epic\n";
    private final File file;
    private static final CSVTaskUtils csvTaskUtils = new CSVTaskUtils();

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() {
        try {
            Writer fileWriter = new FileWriter(file);
            Map<Integer, Task> currentTasks;
            currentTasks = super.getTasks();
            fileWriter.write(HEADER);
            for (Task currentTask : currentTasks.values()) {
                fileWriter.write(csvTaskUtils.toString(currentTask) + "\n");
            }
            Map<Integer, Subtask> currentSubtasks;
            currentSubtasks = super.getSubtasks();
            for (Subtask curSubtask : currentSubtasks.values()) {
                fileWriter.write(csvTaskUtils.toString(curSubtask) + "\n");
            }
            Map<Integer, Epic> currentEpics;
            currentEpics = super.getEpics();
            for (Epic currentEpic : currentEpics.values()) {
                fileWriter.write(csvTaskUtils.toString(currentEpic) + "\n");
            }
            fileWriter.write("\n");

            fileWriter.write(csvTaskUtils.historyToString(super.getInMemoryHistoryManager()));

            fileWriter.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка: " + e.getMessage());
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        try {
            FileBackedTasksManager manager = new FileBackedTasksManager(file);
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);

            while (br.ready()) {
                String line = br.readLine();
                if (line.contains("Description ")) {
                    String[] details = line.split(",");
                    TypeOfTask currentType = TypeOfTask.valueOf(details[1]);
                    switch (currentType) {
                        case TASK -> manager.getTasks().put(csvTaskUtils.fromString(line).getId(),
                                csvTaskUtils.fromString(line));
                        case SUBTASK -> manager.getSubtasks().put(csvTaskUtils.fromString(line).getId(),
                                (Subtask) csvTaskUtils.fromString(line));
                        case EPIC -> manager.getEpics().put(csvTaskUtils.fromString(line).getId(),
                                (Epic) csvTaskUtils.fromString(line));
                    }
                } else if (line.contains("id")) {
                } else if (line.isBlank()) {
                } else {
                    List<Integer> currentList;
                    currentList = csvTaskUtils.historyFromString(line);
                    for (Integer currentCount : currentList) {
                        if (manager.getTasks().containsKey(currentCount)) {
                            manager.getInMemoryHistoryManager().addTask(manager.getTasks().get(currentCount));
                        } else if (manager.getSubtasks().containsKey(currentCount)) {
                            manager.getInMemoryHistoryManager().addTask(manager.getSubtasks().get(currentCount));
                        } else if (manager.getEpics().containsKey(currentCount)) {
                            manager.getInMemoryHistoryManager().addTask(manager.getEpics().get(currentCount));
                        }
                    }
                }
            }
            return manager;
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка: " + e.getMessage());
        }
    }

    @Override
    public void createNewTask(Task task) {
        super.createNewTask(task);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task getTaskById(int newId) {
        super.getTaskById((newId));
        save();
        return tasks.get(newId);
    }

    @Override
    public void deleteTask(int newId) {
        super.deleteTask(newId);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void createNewEpic(Epic epic) {
        super.createNewEpic(epic);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Epic getEpicById(int newId) {
        super.getEpicById(newId);
        save();
        return epics.get(newId);
    }

    @Override
    public void deleteEpic(int newId) {
        super.deleteEpic(newId);
        save();
    }


    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        super.createNewSubtask(subtask);
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Subtask getSubtaskById(int newId) {
        super.getSubtaskById(newId);
        save();
        return subtasks.get(newId);
    }

    @Override
    public void deleteSubtask(int newId) {
        super.deleteSubtask(newId);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }
}


