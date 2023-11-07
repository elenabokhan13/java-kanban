package yandex.practicum.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yandex.practicum.service.InMemoryTaskManager;
import yandex.practicum.service.Managers;

import java.io.IOException;

public class InMemoryTaskManagerTest extends TaskManagerTest {

    @BeforeEach
    public void createManager() throws IOException, InterruptedException {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }

    @Test
    @Override
    public void createNewTaskTest() throws IOException, InterruptedException {
        super.createNewTaskTest();
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
