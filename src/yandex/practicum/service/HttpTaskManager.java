package yandex.practicum.service;

import com.google.gson.Gson;
import yandex.practicum.client.KVTaskClient;
import yandex.practicum.exceptions.ManagerSaveException;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class HttpTaskManager extends FileBackedTasksManager {
    private final String url;
    private KVTaskClient kvTaskClient;
    private Gson gson = new Gson();

    public HttpTaskManager(String url) throws IOException, InterruptedException {
        super(new File(url));
        this.url = "http://" + url;
        kvTaskClient = new KVTaskClient(this.url);
    }

    public KVTaskClient getKvTaskClient() {
        return kvTaskClient;
    }

    @Override
    public void createNewTask(Task task) throws IOException, InterruptedException {
        super.createNewTask(task);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public void deleteAllTasks() throws IOException, InterruptedException {
        super.deleteAllTasks();
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public Task getTaskById(int newId) throws IOException, InterruptedException {
        super.getTaskById((newId));
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
        return tasks.get(newId);
    }

    @Override
    public void deleteTask(int newId) throws IOException, InterruptedException {
        super.deleteTask(newId);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public void updateTask(Task task) throws IOException, InterruptedException {
        super.updateTask(task);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public void createNewEpic(Epic epic) throws IOException, InterruptedException {
        super.createNewEpic(epic);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public void deleteAllEpics() throws IOException, InterruptedException {
        super.deleteAllEpics();
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public Epic getEpicById(int newId) throws IOException, InterruptedException {
        super.getEpicById(newId);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
        return epics.get(newId);
    }

    @Override
    public void deleteEpic(int newId) throws IOException, InterruptedException {
        super.deleteEpic(newId);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public void updateEpic(Epic epic) throws IOException, InterruptedException {
        super.updateEpic(epic);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public void createNewSubtask(Subtask subtask) throws IOException, InterruptedException {
        super.createNewSubtask(subtask);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public void deleteAllSubtasks() throws IOException, InterruptedException {
        super.deleteAllSubtasks();
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public Subtask getSubtaskById(int newId) throws IOException, InterruptedException {
        super.getSubtaskById(newId);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
        return subtasks.get(newId);
    }

    @Override
    public void deleteSubtask(int newId) throws IOException, InterruptedException {
        super.deleteSubtask(newId);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    @Override
    public void updateSubtask(Subtask subtask) throws IOException, InterruptedException {
        super.updateSubtask(subtask);
        kvTaskClient.put(kvTaskClient.getTokenApiClient(), fileToString(file));
    }

    private String fileToString(File file) {
        String response = "";
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);

            while (br.ready()) {
                String line = br.readLine();
                response = response + "\n" + line;
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка: " + e.getMessage());
        }
        return response;
    }
}
