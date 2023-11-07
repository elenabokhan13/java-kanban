package yandex.practicum.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import yandex.practicum.service.Managers;
import yandex.practicum.service.TaskManager;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Subtask;
import yandex.practicum.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private HttpServer httpServer;
    private TaskManager fileBackedTasksManager;
    private Gson gson;

    public HttpTaskServer() throws IOException, InterruptedException {
        this(Managers.getDefault());
    }

    public HttpTaskServer(TaskManager fileBackedTasksManager) throws IOException {
        this.fileBackedTasksManager = fileBackedTasksManager;
        gson = Managers.getGson();
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress("localhost", PORT), 0);
        httpServer.createContext("/tasks", this::handleTasks);
    }

    private void handleTasks(HttpExchange httpExchange) {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String requestMethod = httpExchange.getRequestMethod();
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/task/$", path)) {
                        String response = gson.toJson(fileBackedTasksManager.getTasks());
                        sendText(httpExchange, response);
                    } else if (Pattern.matches("^/tasks/task/id=\\d+$", path)) {
                        String taskId = path.replaceFirst("/tasks/task/id=", "");
                        int id = parsePathId(taskId);
                        if (id != -1) {
                            String response = gson.toJson(fileBackedTasksManager.getTaskById(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен неверный номер задачи.");
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    } else if (Pattern.matches("^/tasks/subtask/$", path)) {
                        String response = gson.toJson(fileBackedTasksManager.getSubtasks());
                        sendText(httpExchange, response);
                    } else if (Pattern.matches("^/tasks/subtask/id=\\d+$", path)) {
                        String subtaskId = path.replaceFirst("/tasks/subtask/?id=", "");
                        int id = parsePathId(subtaskId);
                        if (id != -1) {
                            String response = gson.toJson(fileBackedTasksManager.getSubtaskById(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен неверный номер подзадачи.");
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    } else if (Pattern.matches("^/tasks/epic/$", path)) {
                        String response = gson.toJson(fileBackedTasksManager.getEpics());
                        sendText(httpExchange, response);
                    } else if (Pattern.matches("^/tasks/epic/id=\\d+$", path)) {
                        String epicId = path.replaceFirst("/tasks/epic/id=", "");
                        int id = parsePathId(epicId);
                        if (id != -1) {
                            String response = gson.toJson(fileBackedTasksManager.getEpicById(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен неверный номер эпика.");
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    } else if (Pattern.matches("^/tasks/history/$", path)) {
                        String response = gson.toJson(fileBackedTasksManager.getHistory());
                        sendText(httpExchange, response);
                    } else if (Pattern.matches("^/tasks/$", path)) {
                        String response = gson.toJson(fileBackedTasksManager.getPrioritizedTasks());
                        sendText(httpExchange, response);
                    } else if (Pattern.matches("^/tasks/subtask/epic/id=\\d+$", path)) {
                        String epicId = path.replaceFirst("/tasks/subtask/epic/?id=", "");
                        int id = parsePathId(epicId);
                        if (id != -1) {
                            String response = gson.toJson(fileBackedTasksManager
                                    .printSubtasksInEpicList(fileBackedTasksManager.getEpicById(id)));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен неверный номер эпика.");
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    } else {
                        httpExchange.sendResponseHeaders(405, 0);
                    }
                    break;
                }
                case "POST": {
                    if (Pattern.matches("^/tasks/task/$", path)) {
                        InputStreamReader streamReader = new InputStreamReader(httpExchange.getRequestBody());
                        BufferedReader bufferedReader = new BufferedReader(streamReader);
                        String body = bufferedReader.lines().collect(Collectors.joining("\n"));
                        Task task = gson.fromJson(body, Task.class);
                        for (Task taskNew : fileBackedTasksManager.getTasks().values()) {
                            if (taskNew.getId() == task.getId()) {
                                fileBackedTasksManager.updateTask(task);
                                System.out.println("Обновили задачу.");
                                httpExchange.sendResponseHeaders(200, 0);
                                break;
                            }
                        }
                        fileBackedTasksManager.createNewTask(task);
                        System.out.println("Создали задачу.");
                        httpExchange.sendResponseHeaders(200, 0);
                    } else if (Pattern.matches("^/tasks/subtask/$", path)) {
                        InputStreamReader streamReader = new InputStreamReader(httpExchange.getRequestBody());
                        BufferedReader bufferedReader = new BufferedReader(streamReader);
                        String body = bufferedReader.lines().collect(Collectors.joining("\n"));
                        Subtask subtask = gson.fromJson(body, Subtask.class);
                        for (Subtask subtaskNew : fileBackedTasksManager.getSubtasks().values()) {
                            if (subtaskNew.getId() == subtask.getId()) {
                                fileBackedTasksManager.updateSubtask(subtask);
                                System.out.println("Обновили подзадачу.");
                                httpExchange.sendResponseHeaders(200, 0);
                            }
                        }
                        fileBackedTasksManager.createNewSubtask(subtask);
                        System.out.println("Создали подзадачу.");
                        httpExchange.sendResponseHeaders(200, 0);
                    } else if (Pattern.matches("^/tasks/epic/$", path)) {
                        InputStreamReader streamReader = new InputStreamReader(httpExchange.getRequestBody());
                        BufferedReader bufferedReader = new BufferedReader(streamReader);
                        String body = bufferedReader.lines().collect(Collectors.joining("\n"));
                        Epic epic = gson.fromJson(body, Epic.class);
                        for (Epic epicNew : fileBackedTasksManager.getEpics().values()) {
                            if (epicNew.getId() == epic.getId()) {
                                fileBackedTasksManager.updateEpic(epic);
                                System.out.println("Обновили эпик.");
                                httpExchange.sendResponseHeaders(200, 0);
                            }
                        }
                        fileBackedTasksManager.createNewEpic(epic);
                        System.out.println("Создали эпик.");
                        httpExchange.sendResponseHeaders(200, 0);
                    } else {
                        httpExchange.sendResponseHeaders(405, 0);
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/task/$", path)) {
                        fileBackedTasksManager.deleteAllTasks();
                        System.out.println("Удалили все задачи.");
                        httpExchange.sendResponseHeaders(200, 0);
                    } else if (Pattern.matches("^/tasks/task/id=\\d+$", path)) {
                        String taskId = path.replaceFirst("/tasks/task/id=", "");
                        int id = parsePathId(taskId);
                        if (id != -1) {
                            fileBackedTasksManager.deleteTask(id);
                            System.out.println("Удалили задачу №" + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Получен неверный номер задачи.");
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    } else if (Pattern.matches("^/tasks/subtask/$", path)) {
                        fileBackedTasksManager.deleteAllSubtasks();
                        System.out.println("Удалили все подзадачи.");
                        httpExchange.sendResponseHeaders(200, 0);
                    } else if (Pattern.matches("^/tasks/subtask/id=\\d+$", path)) {
                        String subtaskId = path.replaceFirst("/tasks/subtask/?id=", "");
                        int id = parsePathId(subtaskId);
                        if (id != -1) {
                            fileBackedTasksManager.deleteSubtask(id);
                            System.out.println("Удалили подзадачу №" + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Получен неверный номер подзадачи.");
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    } else if (Pattern.matches("^/tasks/epic/$", path)) {
                        fileBackedTasksManager.deleteAllEpics();
                        System.out.println("Удалили все эпики.");
                        httpExchange.sendResponseHeaders(200, 0);
                    } else if (Pattern.matches("^/tasks/epic/id=\\d+$", path)) {
                        String epicId = path.replaceFirst("/tasks/epic/id=", "");
                        int id = parsePathId(epicId);
                        if (id != -1) {
                            fileBackedTasksManager.deleteEpic(id);
                            System.out.println("Удалили эпик №" + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Получен неверный номер эпика.");
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    }
                }
                default: {
                    System.out.println("Полученный запрос не поддерживается.");
                    httpExchange.sendResponseHeaders(405, 0);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private int parsePathId(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}
