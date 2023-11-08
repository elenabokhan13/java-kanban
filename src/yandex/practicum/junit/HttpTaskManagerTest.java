package yandex.practicum.junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yandex.practicum.client.KVTaskClient;
import yandex.practicum.server.KVServer;
import yandex.practicum.service.Managers;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpTaskManagerTest extends TaskManagerTest {

    private KVServer kvServer = new KVServer();
    KVTaskClient kvTaskClient;

    HttpTaskManagerTest() throws IOException {
    }

    @BeforeEach
    public void startServer() throws IOException, InterruptedException {
        kvServer.start();
        taskManager = Managers.getDefault();
        kvTaskClient = new KVTaskClient("http://localhost:8078");
    }

    @AfterEach
    public void stopServer() {
        kvServer.stop();
    }

    @Test
    @Override
    public void createNewTaskTest() throws IOException, InterruptedException {
        super.createNewTaskTest();
        String response = kvTaskClient.load(kvTaskClient.getTokenApiClient());
        String responseExpected = "\nid,type,name,status,description,start_time,end_time,duration,epic" +
                "\n1,TASK,task1,NEW,Description task1,19.05.2023_12:00,19.05.2023_12:35,35,\n\n1";

        assertEquals(responseExpected, response);
    }

    @Test
    @Override
    public void getTasksTest() throws IOException, InterruptedException {
        super.getTasksTest();
    }

    @Test
    @Override
    public void getPrioritizedTasksTest() throws IOException, InterruptedException {
        super.getPrioritizedTasksTest();
    }

    @Test
    @Override
    public void deleteAllTasksTest() throws IOException, InterruptedException {
        super.deleteAllTasksTest();
        final NoSuchElementException exception = assertThrows(NoSuchElementException.class, ()
                -> taskManager.getHistory());
    }

    @Test
    @Override
    public void getTaskByIdTest() throws IOException, InterruptedException {
        super.getTaskByIdTest();
    }

    @Test
    @Override
    public void deleteTaskTest() throws IOException, InterruptedException {
        super.deleteTaskTest();
    }

    @Test
    @Override
    public void updateTaskTest() throws IOException, InterruptedException {
        super.updateTaskTest();
    }

    @Test
    @Override
    public void createNewEpicTest() throws IOException, InterruptedException {
        super.createNewEpicTest();
        String response = kvTaskClient.load(kvTaskClient.getTokenApiClient());
        String responseExpected = "\nid,type,name,status,description,start_time,end_time,duration,epic" +
                "\n1,EPIC,epic1,NEW,Description epic1,\n\n1";

        assertEquals(responseExpected, response);
    }

    @Test
    @Override
    public void getEpicsTest() throws IOException, InterruptedException {
        super.getEpicsTest();
    }

    @Test
    @Override
    public void deleteAllEpicsTest() throws IOException, InterruptedException {
        super.deleteAllEpicsTest();
    }

    @Test
    @Override
    public void getEpicByIdTest() throws IOException, InterruptedException {
        super.getEpicByIdTest();
    }

    @Test
    @Override
    public void deleteEpicTest() throws IOException, InterruptedException {
        super.deleteEpicTest();
    }

    @Test
    @Override
    public void updateEpicTest() throws IOException, InterruptedException {
        super.updateEpicTest();
    }

    @Test
    @Override
    public void createNewSubtaskTest() throws IOException, InterruptedException {
        super.createNewSubtaskTest();
        String response = kvTaskClient.load(kvTaskClient.getTokenApiClient());
        String responseExpected = "\nid,type,name,status,description,start_time,end_time,duration,epic" +
                "\n2,SUBTASK,sub task1,NEW,Description sub task1,19.05.2023_12:00,19.05.2023_12:55,55,1" +
                "\n1,EPIC,epic1,NEW,Description epic1,19.05.2023_12:00,19.05.2023_12:55,55,\n\n2";

        assertEquals(responseExpected, response);
    }

    @Test
    @Override
    public void getSubtasksTest() throws IOException, InterruptedException {
        super.getSubtasksTest();
    }

    @Test
    @Override
    public void deleteAllSubtasksTest() throws IOException, InterruptedException {
        super.deleteAllSubtasksTest();
    }

    @Test
    @Override
    public void getSubtaskByIdTest() throws IOException, InterruptedException {
        super.getSubtaskByIdTest();
    }

    @Test
    @Override
    public void deleteSubtaskTest() throws IOException, InterruptedException {
        super.deleteSubtaskTest();
    }

    @Test
    @Override
    public void updateSubtaskTest() throws IOException, InterruptedException {
        super.updateSubtaskTest();
    }
}